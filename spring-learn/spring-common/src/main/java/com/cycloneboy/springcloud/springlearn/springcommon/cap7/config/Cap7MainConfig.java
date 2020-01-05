package com.cycloneboy.springcloud.springlearn.springcommon.cap7.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap7.model.Bike;
import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@ComponentScan("com.cycloneboy.springcloud.springlearn.springcommon.cap7.model")
@Configuration
public class Cap7MainConfig {

  @Bean("order01")
  public Order order() {
    log.info("给容器中创建order: order01...........................");
    return new Order("1", "test01");
  }

  /**
   * 多实例,getbean才会初始化
   */
//  @Scope("prototype")
  @Bean(initMethod = "init", destroyMethod = "destroy")
  public Bike bike() {
    return new Bike();
  }
}
