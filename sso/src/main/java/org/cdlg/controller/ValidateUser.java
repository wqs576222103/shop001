package org.cdlg.controller;

import org.cdlg.bean.User;
import org.cdlg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: wqs
 * @Date: 2018/8/20 0020 13:53
 * @Description: I LOVE IT？
 */
@Controller
public class ValidateUser {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/validate" ,method = RequestMethod.GET)
    public  int isEmailEngaged(@RequestParam("email") String email,@RequestParam("r") String random){
        int success=0;
        //查找数据库里有无该邮箱
      User user =userService.queryByEmail(email);
        if (user!=null){
            success=1;
        }
       return success;



    }
}
