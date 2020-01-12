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
@Table(name = "mmall_shipping")
public class Shipping {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer userId;

  private String receiverName;

  private String receiverPhone;

  private String receiverMobile;

  private String receiverProvince;

  private String receiverCity;

  private String receiverDistrict;

  private String receiverAddress;

  private String receiverZip;

  private Date createTime;

  private Date updateTime;

}