package com.cycloneboy.springcloud.springlearn.springcommon.cap9.controller;

import com.cycloneboy.springcloud.springlearn.springcommon.cap9.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Controller
public class OrderController {


  @Qualifier("orderServiceImpl")
//  @Resource
  @Autowired
  public OrderServiceImpl orderService;


}
