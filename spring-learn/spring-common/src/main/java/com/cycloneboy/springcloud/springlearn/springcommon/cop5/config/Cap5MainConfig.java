package com.cycloneboy.springcloud.springlearn.springcommon.cop5.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Create by  sl on 2020-01-04 21:20
 */
//@ComponentScan(value = "com.cycloneboy.springcloud.springlearn.springcommon.cap2",
//    includeFilters = {
//        @Filter(type = FilterType.CUSTOM, classes = {SlTypeFilter.class})
//    }, useDefaultFilters = false)
@Slf4j
@Configuration
public class Cap5MainConfig {


  @Bean("test01")
  public Order order() {
    log.info("给容器中创建order: test01...........................");
    return new Order("1", "test01");
  }

  @Conditional(WindowsCondition.class)
  @Bean("test02")
  public Order order2() {
    log.info("给容器中创建order:WindowsCondition...........................");
    return new Order("2", "WindowsCondition");
  }

  @Conditional(LinuxCondition.class)
  @Bean("test03")
  public Order order3() {
    log.info("给容器中创建order:LinuxCondition...........................");
    return new Order("3", "LinuxCondition");
  }
}
