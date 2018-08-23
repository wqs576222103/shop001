package org.cdlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: wqs
 * @Date: 2018/8/15 0015 15:23
 * @Description: I LOVE IT？
 */
@Controller
public class IndexController {

    @RequestMapping("/{pageName}")
    public String index(@PathVariable("pageName") String pageName){
        return pageName;
    }




}
