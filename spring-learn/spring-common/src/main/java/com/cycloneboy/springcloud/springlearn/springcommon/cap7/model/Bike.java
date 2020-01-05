package com.cycloneboy.springcloud.springlearn.springcommon.cap7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2020-01-05 09:04
 */
@Slf4j
@AllArgsConstructor
@Data
public class Bike {

  private String name;

  private Integer price;

  public Bike() {
    log.info("Bike constructor...");
  }

  public void init() {
    log.info("Bike init...");
  }

  public void destroy() {
    log.info("Bike destroy...");
  }

}
