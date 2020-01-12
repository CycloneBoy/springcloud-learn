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
 * 商品类目
 */
@Data
@Entity
@Table(name = "tb_item_cat")
public class ItemCat implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * 父分类ID=0时代表一级根分类
   */
  private Long parentId;

  private String name;

  /**
   * 状态 1启用 0禁用
   */
  private Integer status;

  /**
   * 排列序号
   */
  private Integer sortOrder;

  /**
   * 是否为父分类 1为true 0为false
   */
  private Boolean isParent;

  private Date created;

  private Date updated;

  private String icon;

  /**
   * 备注
   */
  private String remark;

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public void setIcon(String icon) {
    this.icon = icon == null ? null : icon.trim();
  }

  public void setRemark(String remark) {
    this.remark = remark == null ? null : remark.trim();
  }
}