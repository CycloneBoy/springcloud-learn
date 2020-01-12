package com.cycloneboy.springcloud.slmall.module.mmall.entity;

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
@Table(name = "mmall_order_item")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Long orderNo;

  private Integer productId;

  private String productName;

  private String productImage;

  private BigDecimal currentUnitPrice;

  private Integer quantity;

  private BigDecimal totalPrice;

  private Date createTime;

  private Date updateTime;

  private Integer userId;

}