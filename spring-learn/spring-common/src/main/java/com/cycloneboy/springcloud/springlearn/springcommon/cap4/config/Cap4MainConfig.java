package com.cycloneboy.springcloud.springlearn.springcommon.cap4.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Create by  sl on 2020-01-04 21:20
 */
//@ComponentScan(value = "com.cycloneboy.springcloud.springlearn.springcommon.cap2",
//    includeFilters = {
//        @Filter(type = FilterType.CUSTOM, classes = {SlTypeFilter.class})
//    }, useDefaultFilters = false)
@Slf4j
@Configuration
public class Cap4MainConfig {


  @Lazy
  @Bean
  public Order order() {
    log.info("给容器中创建order...........................");
    return new Order("1", "test01");
  }
}
