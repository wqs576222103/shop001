package org.cdlg.service.Impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.cdlg.bean.User;
import org.cdlg.common.JsonUtils;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.mapper.UserMapper;
import org.cdlg.service.JedisService;
import org.cdlg.service.SmsService;
import org.cdlg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import java.util.Date;


/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 14:35
 * @Description: I LOVE IT？
 */
@Service
public class UserServiceImpl implements UserService {
   private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private JedisService jedisService;
    @Autowired
    UserMapper userMapper;
    private static final String USER_LOGIN="welcometowqs:";

    @Override
    public void register(User user) throws CustomException {
        //用户名 电话名不重复
       if (queryByPhone(user.getPhone())!=null){
           LOGGER.info("用户名已经被注册，用户名是：{}",user.getUsername());
           throw  new CustomException("手机号已经被注册了");
        }
        if (queryByUser(user.getUsername())!=null){
            throw new CustomException("用户名已经被注册了");
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));//密码加密
        user.setCreated(new Date());
        user.setUpdated(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public User queryByUser(String username) {
        User user=new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    @Override
    public User queryByPhone(String phone) {
        User user=new User();
        user.setPhone(phone);
        return userMapper.selectOne(user);
    }

    @Override
    public User queryByEmail(String email) {
        User user=new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }

    @Override
    public Result login(String username, String password) /*throws CustomException*/ {
        if (StringUtil.isEmpty(username)||StringUtil.isEmpty(password)){
            //throw new CustomException("用户名或密码为空");
            return ResultUtils.buildFail(401,"用户名或密码为空");
        }

        String md5Password=DigestUtils.md5Hex(password);
        User record=new User();
        record.setUsername(username);
        record.setPassword(md5Password);
        //还应加一个电话登录的验证
        User user=userMapper.selectOne(record);
        if (user==null){
            return ResultUtils.buildFail(401,"用户名或密码错误");
        }
        String token=DigestUtils.md5Hex(new Date().toString()+username);
        jedisService.set(USER_LOGIN+token, JsonUtils.objectToJson(user));
        jedisService.expire(USER_LOGIN+token,60*60*2);

        return ResultUtils.buildSuccess(token);
    }

    @Override
    public String loginByPhone(String phone, String code) throws CustomException {
        //从redis获取验证码，校验
        String service_code=jedisService.get(SmsService.SMS +SmsService.LOGIN_SMS+phone);
        if (StringUtil.isEmpty(service_code)){
            throw new  CustomException("验证码过期了");
        }
        if (!service_code.equals(code)){
            throw new  CustomException("验证码错误");
        }
       //登录，将信息存入缓存
        User user=queryByPhone(phone);
        if (user==null){
            throw  new  CustomException("手机号未注册");
        }
        String token=DigestUtils.md5Hex(new Date().toString()+user.getUsername());
        jedisService.set(USER_LOGIN+token, JsonUtils.objectToJson(user));
        jedisService.expire(USER_LOGIN+token,60*60*2);

        return token;
    }

    @Override
    public Boolean registerByPhone(String phone, String code)  {
        String service_code=jedisService.get(SmsService.SMS +SmsService.LOGIN_SMS+phone);
        if (StringUtil.isEmpty(service_code)){
            LOGGER.error("验证码过期了");
           return false;
        }
        if (!service_code.equals(code)){
            LOGGER.error("验证码错误");
            return false;
        }else {
            LOGGER.info("验证码正确，可以注册");
            return true;
        }
    }

    public   Boolean check(String param,Integer type){
        if (type<1||type>3){
            //错误的参数类型
            return false;
        }
        User record=new User();
        switch (type){
            case 1:
                record=this.queryByUser(param);
                break;
            case 2:
                record=this.queryByPhone(param);
                break;
            case 3:
                record=this.queryByEmail(param);

        }
        return record!=null;
    }

    @Override
    public User queryUserByToken(String token) throws CustomException {
        return null;
    }
}
