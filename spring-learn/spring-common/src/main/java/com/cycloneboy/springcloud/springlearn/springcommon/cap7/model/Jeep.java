package com.cycloneboy.springcloud.springlearn.springcommon.cap7.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2020-01-05 10:15
 */
@Slf4j
@AllArgsConstructor
@Data
@Component
public class Jeep {

  private String name;

  public Jeep() {
    log.info("Jeep constructor...");
  }

  @PostConstruct
  public void init() {
    log.info("Jeep init @PostConstruct...");
  }

  @PreDestroy
  public void destroy() {
    log.info("Jeep destroy @PreDestroy...");
  }

}
