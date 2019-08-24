package com.cycloneboy.springcloud.apigateway.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-24 11:41
 */
@Slf4j
@RestController
public class HelloController {

  @GetMapping("/local/hello")
  public BaseResponse hello() {
    return new BaseResponse("hello world local");
  }

}
