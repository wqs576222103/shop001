package org.cdlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 16:46
 * @Description: I LOVE IT？
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @RequestMapping("/register")
    public String userRegister(){
        return "register";
    }
    @RequestMapping("/login")
    public String userLogin(){
        return "login";
    }

}
