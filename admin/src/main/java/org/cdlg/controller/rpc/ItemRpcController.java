package org.cdlg.controller.rpc;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.service.ItemDescService;
import org.cdlg.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: wqs
 * @Date: 2018/8/15 0015 18:34
 * @Description: I LOVE IT？
 */
@Controller
@RequestMapping("/rpc/item")
public class ItemRpcController {


    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result queryItem(@PathVariable("itemId")Long itemId ){
        Item item = itemService.queryById(itemId);
        if(item==null){
            return ResultUtils.buildFail(101,"查询的商品基本信息不存在，id:"+itemId);
        }
        return ResultUtils.buildSuccess(item);
    }


    @RequestMapping(value = "/desc/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result  queryItemDesc(@PathVariable("itemId")Long itemId ){
        ItemDesc itemDesc = itemDescService.queryById(itemId);
        if(itemDesc==null){
            return ResultUtils.buildFail(102,"查询的商品详情不存在,id:"+itemId);
        }
        return ResultUtils.buildSuccess(itemDesc);
    }
}
