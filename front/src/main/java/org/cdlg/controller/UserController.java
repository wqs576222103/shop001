package org.cdlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 16:46
 * @Description: I LOVE IT？
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @RequestMapping(value="{pageName}",method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
    }


}
