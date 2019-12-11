package com.cycloneboy.springcloud.goodskill.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by  sl on 2019-12-11 09:50
 */
@Slf4j
@Api(tags = "商品详情页面")
@Controller
@RequestMapping("/")
public class IndexController {

  /**
   * 跳转商品详情页面
   *
   * @param pageid
   * @return
   */
  @GetMapping("/goods")
  public String getGoodsPage(@RequestParam Integer pageid) {

    return pageid.toString();
  }
}
