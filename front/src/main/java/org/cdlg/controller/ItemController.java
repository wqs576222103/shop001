package org.cdlg.controller;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.ItemService;
import org.cdlg.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @Auther: wqs
 * @Date: 2018/8/16 0016 22:33
 * @Description: I LOVE IT？
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private  JedisService jedisService;


    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public  String toItem(Model model, @PathVariable("itemId") Long itemId) throws CustomException, IOException
    {
        Item item=itemService.queryItemById(itemId);
        String []images=item.getImage().split(",");
        if (images==null|| images.length==0){
            throw  new  CustomException("商品图片不存在，请联系管理员");
        }
        ItemDesc itemDesc=itemService.queryItemDescById(itemId);
     //System.out.println(images);
       // model.addAttribute("images",images);
        item.setImages(images);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);

        return "item";
    }

    @RequestMapping(value = "/cache/delete/{itemId}",method = RequestMethod.GET)
    public Result deleteCache(@PathVariable("itemId") Long itemId)
    {
         jedisService.hdel("item",itemId+"");
         return ResultUtils.buildSuccess();
    }
}
