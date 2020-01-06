package com.cycloneboy.springcloud.springlearn.springcommon.cap12.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap12.model.Moon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@ComponentScan("com.cycloneboy.springcloud.springlearn.springcommon.cap12")
@Configuration
public class Cap12MainConfig {

  @Bean
  public Moon getMoon() {
    return new Moon();
  }
}
