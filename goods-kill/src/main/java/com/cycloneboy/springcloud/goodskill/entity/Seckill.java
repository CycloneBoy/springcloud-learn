package com.cycloneboy.springcloud.goodskill.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;

/**
 * Create by  sl on 2019-12-10 17:28
 */
@Data
@Entity
@Table(name = "seckill")
public class Seckill implements Serializable {

  private static final long serialVersionUID = 5170106612856498677L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "seckill_id", nullable = false)
  private long seckillId;

  private String name;

  /**
   * 库存数量
   */
  private int number;

  private Timestamp startTime;

  private Timestamp endTime;

  private Timestamp createTime;

  @Version
  private int version;

}
