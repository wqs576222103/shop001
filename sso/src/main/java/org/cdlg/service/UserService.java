package org.cdlg.service;

import org.cdlg.bean.User;
import org.cdlg.exception.CustomException;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 14:35
 * @Description: I LOVE IT？
 */
public interface UserService  {

    public  void register(User user) throws CustomException;
    //查询有没有来注册
    public User queryByUser(String username);
    public  User queryByPhone(String phone);
    public User queryByEmail(String email);

     //用户密码登陆
    public String login(String username,String password) throws CustomException;
     //手机号验证码登陆
    public String loginByPhone(String phone,String code)throws CustomException;
    //根据令牌查询用户信息(如 第三方token)
    public User queryUserByToken(String token) throws CustomException;

}
