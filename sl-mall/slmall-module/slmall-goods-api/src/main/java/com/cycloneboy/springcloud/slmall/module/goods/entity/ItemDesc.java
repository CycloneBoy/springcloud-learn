package com.cycloneboy.springcloud.slmall.module.goods.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 商品描述表
 */
@Data
@Entity
@Table(name = "tb_item_desc")
public class ItemDesc implements Serializable {

  private static final long serialVersionUID = -1673661850613516304L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long itemId;

  private Date created;

  private Date updated;

  private String itemDesc;

  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc == null ? null : itemDesc.trim();
  }

}