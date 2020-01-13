package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.OrderItem;
import java.util.List;

public interface OrderItemDao extends BaseXCloudDao<OrderItem, Integer> {

  List<OrderItem> findAllByOrderNoAndUserId(Long orderNo, Integer userId);

  List<OrderItem> findAllByOrderNo(Long orderNo);

}