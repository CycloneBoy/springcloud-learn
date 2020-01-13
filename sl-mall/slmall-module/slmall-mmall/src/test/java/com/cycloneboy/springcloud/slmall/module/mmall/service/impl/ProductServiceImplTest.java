package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.SlmallMmallApplicationTests;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by  sl on 2020-01-13 10:11
 */
@Slf4j
public class ProductServiceImplTest extends SlmallMmallApplicationTests {

  @Autowired
  private IProductService productService;

  @Test
  public void getProductByKeywordCategory() {
    ServerResponse<PageInfo> result = productService
        .getProductByKeywordCategory("", 100012, 1, 10, "price_desc");

    log.info("查询的数量: {}", result.getData().getList().size());

    result.getData().getList().forEach(item -> log.info("{}", item));
  }
}