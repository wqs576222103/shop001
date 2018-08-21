package org.cdlg.service.rpc;

import org.cdlg.bean.Item;
import org.cdlg.bean.ItemDesc;
import org.springframework.stereotype.Service;

/**
 * @Auther: wqs
 * @Date: 2018/8/20 0020 16:38
 * @Description: I LOVE IT？
 */
@Service
public interface ItemRpcService {
    public Item queryItemById(Long itemId);

    public ItemDesc queryItemDescById(Long itemId);
}
