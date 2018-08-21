package org.cdlg.controller;

import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 19:37
 * @Description: I LOVE IT？
 */
@RequestMapping("/sms")
@Controller
public class SmsController {
    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/login/{phone}",method = RequestMethod.POST)
    @ResponseBody
    public Result sendLoginSms(@PathVariable("phone")String phone ) throws CustomException {
        smsService.sendLoginSms(phone);
        return ResultUtils.buildSuccess();
    }
}
