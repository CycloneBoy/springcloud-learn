package com.cycloneboy.springcloud.springlearn.springcommon.cap9;

import com.cycloneboy.springcloud.springlearn.springcommon.cap9.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Configuration
@ComponentScan({"com.cycloneboy.springcloud.springlearn.springcommon.cap9.controller",
    "com.cycloneboy.springcloud.springlearn.springcommon.cap9.service",
    "com.cycloneboy.springcloud.springlearn.springcommon.cap9.dao"
})
@PropertySource(value = "classpath:/application.properties")
public class Cap9MainConfig {

  //  @Primary
  @Bean("orderDao2")
  public OrderDao orderDao() {
    OrderDao orderDao = new OrderDao();
    orderDao.setFlag("2");

    return orderDao;
  }

}
