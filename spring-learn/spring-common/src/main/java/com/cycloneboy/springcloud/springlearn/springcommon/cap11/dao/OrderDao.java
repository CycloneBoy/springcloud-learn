package com.cycloneboy.springcloud.springlearn.springcommon.cap11.dao;

import com.cycloneboy.springcloud.springlearn.springcommon.cap11.model.Order;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2020-01-05 15:59
 */
@Slf4j
@Repository
public class OrderDao {

  //  @Autowired
  private JdbcTemplate jdbcTemplate;

  public int insert(@NotNull Order order) {
    String sql = "insert into success_killed (seckill_id, user_id,state,create_time) VALUES(?,?,?,?)";
    int result = jdbcTemplate.update(sql, order.getSeckillId(), order.getUserId(), order.getState(),
        order.getCreateTime());
    log.info("insert: {}", result);
    return result;
  }
}
