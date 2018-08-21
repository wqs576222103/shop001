package org.cdlg.service.rpc;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;

/**
 * @Auther: wqs
 * @Date: 2018/8/20 0020 16:06
 * @Description: I LOVE IT？
 */
public interface ItemRpcService {
    public Item queryItemById(Long  itemId);
    public ItemDesc queryItemDescById(Long itemId);
}
