package com.cycloneboy.springcloud.springlearn.springcommon.cap11.service;

import com.cycloneboy.springcloud.springlearn.springcommon.cap11.dao.OrderDao;
import com.cycloneboy.springcloud.springlearn.springcommon.cap11.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-05 15:59
 */
@Slf4j
@Service
public class OrderService {

  @Autowired
  private OrderDao orderDao;

  public int save(Order order) {
    log.info("save order 完成.....");
    return orderDao.insert(order);

  }
}
