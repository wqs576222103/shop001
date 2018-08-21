package org.cdlg.controller;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 22:25
 * @Description: I LOVE IT？
 */

import com.github.pagehelper.PageInfo;
import org.cdlg.bean.Item;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.cdlg.result.EasyUIResult;
import org.cdlg.result.nameDescResult;
import org.cdlg.service.ItemCatService;
import org.cdlg.service.ItemDescService;
import org.cdlg.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private  ItemCatService itemCatService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result itemAdd(Item item, String desc) {
        itemService.AddItem(item, desc);


        return ResultUtils.buildSuccess();

    }

   /* @RequestMapping(value = "/desc/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public ItemDesc desc(@PathVariable("itemId") Long itemId) {

        return itemDescService.queryById(itemId);
    }*/
   @RequestMapping(value = "/desc")
   @ResponseBody
   public nameDescResult desc(@RequestParam(value = "itemId") Long itemId, @RequestParam(value = "cid") Long cid) {

       String desc= itemDescService.queryById(itemId).getItemDesc();
       String name= itemCatService.queryById(cid).getName();
       nameDescResult nameDescResult=new nameDescResult();
       nameDescResult.setDesc(desc);
       nameDescResult.setName(name);
       return nameDescResult;
   }
//有 get post请求，都可以请求
    @RequestMapping(value = "/list")
    @ResponseBody
    public EasyUIResult list(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows) {
        PageInfo<Item> pageInfo = itemService.queryPageListByCondition(page, rows, null);
        EasyUIResult easyUIResult = new EasyUIResult((int) pageInfo.getTotal(), pageInfo.getList());
        return easyUIResult;
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Result itemUpdate(Item item,String desc){
      itemService.updateItem(item,desc);
      return ResultUtils.buildSuccess();
    }

    @RequestMapping(value="/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public Result unShelve(Long  []ids){
        for (int i = 0; i <ids.length ; i++) {
           Item item= itemService.queryById(ids[i]);
           item.setStatus(1);
           itemService.update(item);
        }

        return ResultUtils.buildSuccess();
    }

}
