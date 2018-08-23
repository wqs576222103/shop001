package org.cdlg.service.impl;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.service.ItemDescService;
import org.cdlg.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 22:34
 * @Description: I LOVE IT？
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
  //不能自己注入自己
    /*@Autowired
    ItemService itemService;*/
    @Autowired
    ItemDescService itemDescService;
    @Autowired
    private JmsTemplate jmsTemplate;




    @Override
    public void AddItem(Item item, String desc) {
        //设置商品为下架
        item.setStatus(2);

        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        this.save(item);

        ItemDesc itemDesc=new ItemDesc();

        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getCreated());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);


    }

    @Override
    public void updateItem(Item item, String desc) {
        item.setUpdated(new Date());

        ItemDesc itemDesc=itemDescService.queryById(item.getId());
        itemDesc.setUpdated(item.getUpdated());
        itemDesc.setItemDesc(desc);
        //请求前台删除商品在内存中的缓存
        //方法1.传统方式
        //HttpClientUtil.doGet("http://front.cdlg.com/rpc/item/cache/delete/"+item.getId());
        //方法2.加入消息中间件
        ////使用mq 通知消费者
        //生产消息

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(item.getId()+"");
            }
        });

        this.update(item);
        itemDescService.update(itemDesc);


    }
}
