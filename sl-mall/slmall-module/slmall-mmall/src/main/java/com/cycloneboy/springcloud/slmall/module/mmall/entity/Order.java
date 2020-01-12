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
@Table(name = "mmall_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Long orderNo;

  private Integer userId;

  private Integer shippingId;

  private BigDecimal payment;

  private Integer paymentType;

  private Integer postage;

  private Integer status;

  private Date paymentTime;

  private Date sendTime;

  private Date endTime;

  private Date closeTime;

  private Date createTime;

  private Date updateTime;

}