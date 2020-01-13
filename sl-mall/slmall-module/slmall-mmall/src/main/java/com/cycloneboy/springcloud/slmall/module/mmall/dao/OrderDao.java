package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Order;
import java.util.List;

public interface OrderDao extends BaseXCloudDao<Order, Integer> {

  List<Order> findAllByUserId(Integer userId);

  Order findTopByUserIdAndOrderNo(Integer userId, Long orderNo);

}