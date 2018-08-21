package org.cdlg.service;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.cdlg.exception.CustomException;

import java.io.IOException;

/**
 * @Auther: wqs
 * @Date: 2018/8/15 0015 19:18
 * @Description: I LOVE IT？
 */
public interface ItemService {
    public Item queryItemById(Long Itemid) throws IOException, CustomException;
    public ItemDesc queryItemDescById(Long Itemid) throws IOException, CustomException;


}
