package org.cdlg.controller;

import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 21:17
 * @Description: I LOVE IT？
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Result check(@PathVariable("param") String nameOrPhone, @PathVariable("type") Integer type,
                                   @RequestParam("r") String random, @RequestParam("callback") String callback) {

      /*  User user = null;
        if (type == 1) {
            user = userService.queryByUser(nameOrPhone);
        }else if (type == 2) {//手机
            user = userService.queryByPhone(nameOrPhone);
        }
        //跨域问题
        // Result result=ResultUtils.buildSuccess(user);
        // json= JsonUtils.objectToJson(result);
        // String cal=callback+"("+json+")";
        return ResultUtils.buildSuccess(user);*/
      Boolean b=userService.check(nameOrPhone,type);
      return ResultUtils.buildSuccess(b);


    }


    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("callback") String callback) throws CustomException {
     //  System.out.println(username+"--------------------");
     //应先看redis内存中有没有该用户登录状态，有的话只是设置过期时间？获取页面的token不好获取....
        return userService.login(username,password);
    }
}
