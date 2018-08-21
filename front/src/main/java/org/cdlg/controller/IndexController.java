package org.cdlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: wqs
 * @Date: 2018/8/15 0015 15:23
 * @Description: I LOVE IT？
 */
@Controller

public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }




}
