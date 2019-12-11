package com.cycloneboy.springcloud.goodskill.service.impl;

import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by  sl on 2019-12-11 22:06
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceImplTest {

  @Autowired
  private SeckillService seckillService;

  @Test
  public void getById() {
    log.info("seckill: {}", seckillService.getById(1000L).toString());
  }
}