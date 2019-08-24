package com.cycloneboy.springcloud.configclient.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-24 15:36
 */
@RefreshScope
@Slf4j
@RestController
public class TestController {

  @Value("${config.hello}")
  private String from;


  @GetMapping("/hello")
  public BaseResponse hello() {
    return new BaseResponse(this.from);
  }
}
