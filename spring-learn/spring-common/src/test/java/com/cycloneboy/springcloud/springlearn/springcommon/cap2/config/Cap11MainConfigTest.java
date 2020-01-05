package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.cycloneboy.springcloud.springlearn.springcommon.cap11.config.Cap11MainConfig;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap11MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() throws SQLException {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap11MainConfig.class);
    log.info("IOC创建完成...........................");

    DruidDataSource dataSource = app.getBean(DruidDataSource.class);
    DruidPooledConnection connection = dataSource.getConnection();
//
//    OrderService orderService = app.getBean(OrderService.class);
//    int result = orderService
//        .save(new Order(1L, 2L, (short) 0, new Timestamp(System.nanoTime() / 1000)));
//
//    log.info("{}", result);

    app.close();


  }

}