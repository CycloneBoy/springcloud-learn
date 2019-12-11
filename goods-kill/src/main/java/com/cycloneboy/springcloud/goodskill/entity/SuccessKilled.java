package com.cycloneboy.springcloud.goodskill.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Create by  sl on 2019-12-10 17:30
 */
@Data
@Entity
@Table(name = "success_killed")
public class SuccessKilled implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "seckill_id", nullable = false)
  private long seckillId;

  @Id
  private long userId;

  /**
   * 状态标示：-1指无效，0指成功，1指已付款
   */
  private short state;

  private Timestamp createTime;


}
