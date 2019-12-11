package com.cycloneboy.springcloud.goodskill.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.service.CreateHtmlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-12-10 20:46
 */
@Slf4j
@Api(tags = "生成静态商品页")
@RestController
@RequestMapping("/createHtml")
public class CreateHtmlController {

  @Autowired
  private CreateHtmlService createHtmlService;

  @ApiOperation(value = "生成秒杀活动的静态页面", nickname = "环球车队")
  @PostMapping("/start")
  public BaseResponse start() {
    log.info("生成秒杀活动的静态页面");
    return createHtmlService.createAllHtml();
  }
}
