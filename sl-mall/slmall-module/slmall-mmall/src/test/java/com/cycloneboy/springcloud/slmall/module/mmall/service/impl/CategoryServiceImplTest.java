package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;


import com.cycloneboy.springcloud.slmall.module.mmall.SlmallMmallApplicationTests;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by  sl on 2020-01-13 10:02
 */
@Slf4j
public class CategoryServiceImplTest extends SlmallMmallApplicationTests {

  @Autowired
  private ICategoryService categoryService;

  @Test
  public void testSelectCategoryAndChildrenById() {
    ServerResponse<List<Integer>> listServerResponse = categoryService
        .selectCategoryAndChildrenById(0);

    log.info("查询大小: {}", listServerResponse.getData().size());
    listServerResponse.getData().forEach(integer -> log.info("{}", integer));

  }
}