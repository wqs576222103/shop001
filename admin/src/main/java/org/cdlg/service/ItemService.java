package org.cdlg.service;

import org.cdlg.bean.Item;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 22:34
 * @Description: I LOVE IT？
 */

public interface ItemService extends BaseService<Item> {
    /**
     *
     * 功能描述: 商品包括描述存储
     *
     * @param:
     * @return:
     * @auther: wqs
     * @date: 2018/8/13 0013 21:42
     */
    public void AddItem(Item item,String desc);

    public void updateItem(Item item,String desc);

}
