package org.cdlg.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_item_desc")
public class ItemDesc implements Serializable {

    @Id
    @Column(name = "item_id")
    private Long itemId;


    private Date created;


    private Date updated;


    @Column(name = "item_desc")
    private String itemDesc;


    public Long getItemId() {
        return itemId;
    }


    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }


    public Date getCreated() {
        return created;
    }


    public void setCreated(Date created) {
        this.created = created;
    }


    public Date getUpdated() {
        return updated;
    }


    public void setUpdated(Date updated) {
        this.updated = updated;
    }


    public String getItemDesc() {
        return itemDesc;
    }


    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}