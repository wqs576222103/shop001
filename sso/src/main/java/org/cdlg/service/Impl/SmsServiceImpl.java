package org.cdlg.service.Impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.cdlg.common.JsonUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.JedisService;
import org.cdlg.service.SmsService;
import org.cdlg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 19:36
 * @Description: I LOVE IT？
 */
@Service
public class SmsServiceImpl implements SmsService {
    //阿里大于
    /*public  static final String product="Dysmsapi";
    public static final String domain = "dysmsapi.aliyuncs.com";
    public static final String accessKeyId = "LTAIOWxgzN4SNbQP";
    public static final String accessKeySecret = "A9ZXLnOVb1FInPOcW5enU2ENzAfRIR";
    public static final String SMS_LOGIN="sms_login:";
    public static final String SMS_LOGIN_VALIDATE="sms_login_validate:";*/

    private  static  String Url="http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    @Autowired
    private JedisService jedisService;
    @Autowired
    private UserService userService;
    private Logger LOGGER= LoggerFactory.getLogger(SmsServiceImpl.class);

    //互亿无线
    @Override
    public boolean sendSms(String phone, Integer type) throws CustomException {
        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod(Url);


        if (type==3||type==1){
            //判断是否被注册
            if (userService.queryByPhone(phone)==null){
                throw  new CustomException("用户还未被注册");
            }
        }
        client.getParams().setContentCharset("GBK");
        //设置post提交的方式，提交的数据按照 key1=val1&key2=val2
        // 的方式进行编码，key 和 val 都进行了 URL 转码。大部分服务端语言都对这种方式有很好的支持。
        postMethod.setRequestHeader("ContentType",
                "application/x-www-form-urlencoded;charset=GBK");
        int mobile_code=(int)((Math.random()*9+1)*100000);
        String content=new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        //需要互亿无线的数据
        //NameValuePair是简单名称值对节点类型。
        // 多用于Java像url发送Post请求。在发送post请求时用该list来存放参数
        NameValuePair[] data={
               /* new NameValuePair("account","C99407149"),
                new NameValuePair("password","5298f4622af6c455c372a2a239c0ed1f"),*/
                new NameValuePair("account","c56465"),
                new NameValuePair("password","529as8f46af6c455c372a2a239c0ed1f"),
                new NameValuePair("mobile",phone),
                new NameValuePair("content",content),
                new NameValuePair("format","json"),
        };
        //加入数据
        postMethod.setRequestBody(data);
        try {
           //进行请求
            client.executeMethod(postMethod);

            //获取json ,解析json
            String resultJson= postMethod.getResponseBodyAsString();
            Map map= JsonUtils.jsonToPojo(resultJson,Map.class);

            Integer code=(Integer) map.get("code");
            String msg=(String) map.get("msg");
            String smsid = (String)map.get("smsid");

            System.out.println("code:---------"+code);
            System.out.println("msg:---------"+msg);
            System.out.println("smsid:---------"+smsid);
            if (2==code){
                //成功，存入验证码到内存
                jedisService.set(SMS+LOGIN_SMS+phone,mobile_code+"");
                //五分钟内有效,测试时先注销
                //jedisService.expire(SMS+LOGIN_SMS+phone,60*10);
                return  true;
            }else {
                return false;
            }

        }catch (IOException e){
           e.printStackTrace();
        }

              return false;

    }
//阿里大于
   /* @Override
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


    }*/
}
