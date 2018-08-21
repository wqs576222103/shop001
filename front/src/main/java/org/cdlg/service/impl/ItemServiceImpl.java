package org.cdlg.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.common.HttpClientUtil;
import org.cdlg.common.JsonUtils;
import org.cdlg.exception.CustomException;
import org.cdlg.service.ItemService;
import org.cdlg.service.JedisService;
import org.cdlg.service.rpc.ItemRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.io.IOException;

/**
 * @Auther: wqs
 * @Date: 2018/8/15 0015 19:18
 * @Description: I LOVE IT？
 */
@Service
public class ItemServiceImpl implements ItemService {

    public  static final  String ITEM_URL="http://admin.cdlg.com/rest/rpc/item/";
    public  static final  String ITEM_DESC_URL="http://admin.cdlg.com/rest/rpc/item/desc/";

  @Autowired
    JedisService jedisService;
  @Autowired
  ItemRpcService itemRpcService;
    /*@Override
     public Item queryItemById(Long Itemid) throws IOException,CustomException {
        // System.out.println(ITEM_URL+Itemid+"----------------------------");
       String json= HttpClientUtil.doGet(ITEM_URL+Itemid);
       if(StringUtil.isEmpty(json)){
           throw new CustomException("查询基本信息不存在");
       }
         ObjectMapper objectMapper=new ObjectMapper();
         JsonNode jsonNode=objectMapper.readTree(json);
         JsonNode data=jsonNode.get("data");

         String item_data=data.toString();
         return JsonUtils.jsonToPojo(item_data,Item.class);



     }*/
    //20180818
    /*public Item queryItemById(Long Itemid) throws IOException,CustomException {
        //post请求封装的param
        // Map param=new HashMap<>();
        // param.put("itemId",Itemid+"");
        //查询redis是否有缓存
        String json= jedisService.hget("item",Itemid+"");

        if(StringUtil.isEmpty(json)){
            //System.out.println("没有缓存数据---------------");
            json= HttpClientUtil.doGet(ITEM_URL+Itemid);
            jedisService.hset("item",Itemid+"",json);
            if(StringUtil.isEmpty(json)){
                throw new CustomException("查询基本信息不存在");
            }
        }//else{
        //System.out.println("有缓存数据---------------");
        //}

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode data=jsonNode.get("data");

        String item_data=data.toString();
        return JsonUtils.jsonToPojo(item_data,Item.class);



    }*/

    @Override
    public Item queryItemById(Long Itemid) throws IOException, CustomException {
       //从缓存中获取itemID
        String json=jedisService.hget("item",Itemid+"");
        ///从数据库表中获取
        if (StringUtils.isEmpty(json)){
            Item item=itemRpcService.queryItemById(Itemid);
            if (item==null){
                throw new  CustomException("没有该商品信息");
            }
            String itemJSON= JsonUtils.objectToJson(item);
            jedisService.hset("item",Itemid+"",itemJSON);
            jedisService.expire(Itemid+"",2*60*60);
            return item;
        }

        return JsonUtils.jsonToPojo(json,Item.class);

    }


    public ItemDesc queryItemDescById(Long Itemid)throws CustomException,IOException
    {
        String json=HttpClientUtil.doGet(ITEM_DESC_URL+Itemid);
        if (StringUtil.isEmpty(json)){
            throw  new  CustomException("查询的基本信息不存在");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode data=jsonNode.get("data");
        String item_desc_data=data.toString();
        return  JsonUtils.jsonToPojo(item_desc_data,ItemDesc.class);



    }
}
