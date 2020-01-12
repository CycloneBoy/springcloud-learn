package com.cycloneboy.springcloud.slmall.module.mmall.entity;

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
@Table(name = "mmall_pay_info")
public class PayInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer userId;

  private Long orderNo;

  private Integer payPlatform;

  private String platformNumber;

  private String platformStatus;

  private Date createTime;

  private Date updateTime;
}