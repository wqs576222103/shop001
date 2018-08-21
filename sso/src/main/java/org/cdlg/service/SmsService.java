package org.cdlg.service;

import org.cdlg.exception.CustomException;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 19:36
 * @Description: I LOVE IT？
 */
public interface SmsService {

    /**
     * 发送短信
     * @param phone
     */
    public void sendLoginSms(String phone) throws CustomException;
}
