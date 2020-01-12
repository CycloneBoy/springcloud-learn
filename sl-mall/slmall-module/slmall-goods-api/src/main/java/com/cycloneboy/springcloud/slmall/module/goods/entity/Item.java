package com.cycloneboy.springcloud.slmall.module.goods.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 商品表
 */
@Data
@Entity
@Table(name = "tb_item")
public class Item implements Serializable {

  private static final long serialVersionUID = 894408673110750130L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String sellPoint;

  private BigDecimal price;

  /**
   * 库存数量
   */
  private Integer num;

  /**
   * 售卖数量限制
   */
  private Integer limitNum;

  private String image;

  /**
   * 所属分类
   */
  private Long cid;

  /**
   * 商品状态 1正常 0下架
   */
  private Byte status;

  private Date created;

  private Date updated;


  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public void setSellPoint(String sellPoint) {
    this.sellPoint = sellPoint == null ? null : sellPoint.trim();
  }

  public void setImage(String image) {
    this.image = image == null ? null : image.trim();
  }

  public String[] getImages() {
    if (image != null && !"".equals(image)) {
      String[] strings = image.split(",");
      return strings;
    }
    return null;
  }
}