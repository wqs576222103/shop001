package org.cdlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 23:10
 * @Description: I LOVE IT？
 */
@RequestMapping("/page")
@Controller
public class PageController  {
    //进入首页
    @RequestMapping(value = "{pageName}",method = RequestMethod.GET)
    public  String toPage(@PathVariable("pageName") String pageName){
        return  pageName;
    }
}
