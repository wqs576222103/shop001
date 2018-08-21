package org.cdlg.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.cdlg.exception.CustomException;
import org.cdlg.service.JedisService;
import org.cdlg.service.SmsService;
import org.cdlg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 19:36
 * @Description: I LOVE IT？
 */
@Service
public class SmsServiceImpl implements SmsService {
    public  static final String product="Dysmsapi";
    public static final String domain = "dysmsapi.aliyuncs.com";
    public static final String accessKeyId = "LTAIOWxgzN4SNbQP";
    public static final String accessKeySecret = "A9ZXLnOVb1FInPOcW5enU2ENzAfRIR";
    public static final String SMS_LOGIN="sms_login:";
    public static final String SMS_LOGIN_VALIDATE="sms_login_validate:";

    @Autowired

    private JedisService jedisService;

    @Autowired
    private UserService userService;

    private Logger LOGGER= LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    public void sendLoginSms(String phone) throws CustomException {

        if(StringUtils.isEmpty(phone)){
            LOGGER.info("电话号码不能为空!");
            return;
        }
        //判断是否注册
        if(userService.queryByPhone(phone)==null){
            LOGGER.info("手机号码未注册{}",phone);
            throw  new CustomException("手机号码未注册");
        }
        if(!StringUtils.isEmpty(jedisService.get(SMS_LOGIN_VALIDATE+phone))){
            LOGGER.info("已经发送过验证码了，请等待一分钟");
            throw  new CustomException("已经发送过验证码了，请等待一分钟");
        }
        LOGGER.info("发送短信的电话号码为:{}",phone);
        try {
            //阿里大于的默认设置
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("天成商城");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_135325318");
            //生成验证码
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<6;i++){
                int code = new Random().nextInt(10);
                sb.append(code);
            }
            request.setTemplateParam("{\"code\":\""+sb.toString()+"\"}");
            request.setOutId("yourOutId");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            String result=sendSmsResponse.getCode();
            if(result.equals("OK")){
                LOGGER.info("手机号码{}，发送的验证码为{}",phone,sb.toString());
                //存储验证码在redis
                jedisService.set(SMS_LOGIN+phone,sb.toString());
                //设置10分钟失效
                jedisService.expire(SMS_LOGIN+phone,60*10);
                //防止一分钟内重复发送短信
                jedisService.set(SMS_LOGIN_VALIDATE+phone,"SACASCASCA");
                jedisService.expire(SMS_LOGIN_VALIDATE+phone,60);
            }else{
                LOGGER.error("手机号码{},短信发送失败{}",phone,result);
            }

        }catch (Exception e){
            LOGGER.error("调用短信服务发生异常",e.getMessage());
        }


    }
}
