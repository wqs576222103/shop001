package org.cdlg.service.rpc.Impl;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.service.ItemDescService;
import org.cdlg.service.ItemService;
import org.cdlg.service.rpc.ItemRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: wqs
 * @Date: 2018/8/20 0020 16:07
 * @Description: I LOVE IT？
 */
@Service
public class ItemRpcServiceImpl implements ItemRpcService {
    @Autowired
    private ItemService itemService;

    @Autowired
   private ItemDescService itemDescService;
    @Override
    public Item queryItemById(Long itemId) {

        return itemService.queryById(itemId);
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        return itemDescService.queryById(itemId);
    }
}
