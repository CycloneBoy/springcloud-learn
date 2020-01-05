package com.cycloneboy.springcloud.springlearn.springcommon.cap9.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap8.model.Bird;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class Cap9MainConfig {

  @Bean
  public Bird bird() {
    log.info("给容器中创建bird ...........................");
//    return new Bird("bird01", 20);
    return new Bird();
  }

}
