package com.cycloneboy.springcloud.springlearn.springcommon.cap11.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

/**
 * Create by sl on 2020-01-05 16:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements RowMapper<Order> {

  private static final long serialVersionUID = 5170106612856498677L;

  private long seckillId;

  private long userId;

  /**
   * 状态标示：-1指无效，0指成功，1指已付款
   */
  private short state;

  private Timestamp createTime;

  @Override
  public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
    Order order = new Order();
    order.setSeckillId(rs.getLong("seckill_id"));
    order.setUserId(rs.getLong("user_id"));
    order.setState(rs.getShort("state"));
    order.setCreateTime(rs.getTimestamp("create_time"));

    return order;
  }
}