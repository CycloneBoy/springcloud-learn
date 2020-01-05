package com.cycloneboy.springcloud.springlearn.springcommon.cap10.model;

import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2020-01-05 13:58
 */
@Slf4j
public class Calculator {

  // 业务逻辑方法
  public int div(int i, int j) {
    log.info("-----------Calculator.div-----------------");
    return i / j;
  }
}
