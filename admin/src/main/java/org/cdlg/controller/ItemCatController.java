package org.cdlg.controller;

import org.cdlg.bean.ItemCat;
import org.cdlg.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 17:34
 * @Description: I LOVE IT？
 */
/*@Controller
@RequestMapping("/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ItemCat> queryItemCat(@RequestParam(value = "id",defaultValue = "0") Long parentId){
    ItemCat record=new ItemCat();
    record.setParentId(parentId);
    Example example=new Example(ItemCat.class);
    example.excludeProperties("parentId","parentId");
    example.createCriteria().andEqualTo("parentId",parentId);
    return itemCatService.queryListByCondition(record);


    }

}*/
@RequestMapping("/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ItemCat> list(@RequestParam(value ="id",defaultValue = "0") Long parentId) {

        ItemCat record=new ItemCat();
        record.setParentId(parentId);
        return itemCatService.queryListByCondition(record);
    }



}
