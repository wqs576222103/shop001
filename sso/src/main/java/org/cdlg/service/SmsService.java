package org.cdlg.service;

import org.cdlg.exception.CustomException;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 19:36
 * @Description: I LOVE IT？
 */
public interface SmsService {
    //互亿无线 登录 注册 查找
    public static  final  Integer LOGIN_SMS=1;
    public  static  final Integer REGISTER_SMS=2;
    public  static final  Integer FIND_SMS=3;
    public  static  final  String SMS="sms";
    /**
     * 发送短信 阿里大于
     * @param phone
     */
    //public void sendLoginSms(String phone) throws CustomException;
    /**
     *
     * 功能描述: 发送短信 互亿无线
     *
     * @param:
     * @return:
     * @auther: wqs
     * @date: 2018/8/22 0022 12:15
     */
    public  boolean sendSms(String phone,Integer type) throws CustomException;
}
