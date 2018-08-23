package org.cdlg.controller;
import org.cdlg.bean.User;
import org.cdlg.common.JsonUtils;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 15:20
 * @Description: I LOVE IT？
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register")
    @ResponseBody
    public Result register(@Valid User user, BindingResult bindingResult) throws CustomException {
        if (bindingResult.hasErrors()) {
            String message = getString(bindingResult);
            //客户端应当继续发送请求。这个临时响应是用来通知客户端
            // 它的部分请求已经被服务器接收，且仍未被拒绝
            return ResultUtils.buildFail(100, message);
        }


        LOGGER.info("注册信息为：{}", JsonUtils.objectToJson(user));
        userService.register(user);
        return ResultUtils.buildSuccess();

    }
    //短信注册登录
    @RequestMapping(value = "/SMSregister")
    @ResponseBody
    public Result SMSregister(@Valid User user,Integer sms_code, BindingResult bindingResult) throws CustomException {
        if (bindingResult.hasErrors()) {
            String message = getString(bindingResult);
            //客户端应当继续发送请求。这个临时响应是用来通知客户端
            // 它的部分请求已经被服务器接收，且仍未被拒绝
            return ResultUtils.buildFail(100, message);
        }
       Boolean validateCode=userService.registerByPhone(user.getPhone(),sms_code+"");
        if (validateCode){
        LOGGER.info("注册信息为：{}", JsonUtils.objectToJson(user));
        userService.register(user);
        return ResultUtils.buildSuccess();
        }else {
            //验证码错误或过期
            return ResultUtils.buildFail(405,"验证码错误或过期");
        }


    }

    private String getString(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        List<String> list = new ArrayList<>();
        for (ObjectError error : errors) {
            String message = error.getDefaultMessage();
            list.add(message);

        }
        return org.apache.commons.lang3.StringUtils.join(list, ",");
    }
   //輸入手機號和驗證碼用來登陸的，和調用短信接口無關
    @RequestMapping(value = "/loginByPhone", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> loginByPhone(String phone, String sms_code) throws CustomException {
        if (StringUtil.isEmpty(phone)) {
            //成功返回
            return ResponseEntity.ok(ResultUtils.buildFail(101, "手机号不能为空"));
        }
        if (StringUtil.isEmpty(sms_code)) {
            return ResponseEntity.ok(ResultUtils.buildFail(102, "手机验证码不能为空"));
        }
        String token=userService.loginByPhone(phone,sms_code);
        return ResponseEntity.ok(ResultUtils.buildSuccess(token));
    }


}
