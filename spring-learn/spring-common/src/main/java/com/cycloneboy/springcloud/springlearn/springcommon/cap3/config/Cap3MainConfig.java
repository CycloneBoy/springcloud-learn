package com.cycloneboy.springcloud.springlearn.springcommon.cap3.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Create by  sl on 2020-01-04 21:20
 */
//@ComponentScan(value = "com.cycloneboy.springcloud.springlearn.springcommon.cap2",
//    includeFilters = {
//        @Filter(type = FilterType.CUSTOM, classes = {SlTypeFilter.class})
//    }, useDefaultFilters = false)
@Configuration
public class Cap3MainConfig {

  /**
   * prototype: 多实例 <p/> singleton: 单实例(默认)<br/> request: 主要针对web <br/> session: 同一个session<br/>
   */
  @Scope("prototype")
  @Bean
  public Order order() {
    return new Order("1", "test01");
  }
}
