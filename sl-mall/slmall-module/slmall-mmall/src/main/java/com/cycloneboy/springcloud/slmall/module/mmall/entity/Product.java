package com.cycloneboy.springcloud.slmall.module.mmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "mmall_product")
public class Product implements Serializable {

  private static final long serialVersionUID = 5799841563506268308L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer categoryId;

  private String name;

  private String subtitle;

  private String mainImage;

  private String subImages;

  private String detail;

  private BigDecimal price;

  private Integer stock;

  private Integer status;

  private Date createTime;

  private Date updateTime;


}