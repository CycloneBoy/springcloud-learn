package com.cycloneboy.springcloud.springlearn.springcommon.cap7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Create by  sl on 2020-01-05 09:04
 */
@Slf4j
@AllArgsConstructor
@Data
public class Plane implements ApplicationContextAware {

  private String name;

  private Integer price;

  private ApplicationContext applicationContext;

  public Plane() {
    log.info("Plane constructor...");
  }

  public void init() {
    log.info("Plane init...");
  }

  public void destroy() {
    log.info("Plane destroy...");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
