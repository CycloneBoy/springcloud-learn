package com.cycloneboy.springcloud.springlearn.springcommon.cap12.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2020-01-06 08:49
 */
@Slf4j
@AllArgsConstructor
@Data
@Component
public class Moon {

  private String name;

  public Moon() {
    log.info("moon construct.....");
  }

  public void init() {
    log.info("moon init.....");
  }
}
