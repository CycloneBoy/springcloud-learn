package com.cycloneboy.springcloud.springlearn.springcommon.cap9.service.impl;

import com.cycloneboy.springcloud.springlearn.springcommon.cap9.dao.OrderDao;
import com.cycloneboy.springcloud.springlearn.springcommon.cap9.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  @Qualifier("orderDao2")
  @Autowired
  private OrderDao orderDao;


  public void print() {
    log.info("{} - {}", 1, orderDao.toString());
  }

}
