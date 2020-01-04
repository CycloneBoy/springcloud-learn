package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@ComponentScan(value = "com.cycloneboy.springcloud.springlearn.springcommon.cap2",
    includeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = {SlTypeFilter.class})
    }, useDefaultFilters = false)
@Configuration
public class Cap2MainConfig {

  @Bean
  public Order order() {
    return new Order("1", "test01");
  }
}
