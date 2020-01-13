package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Shipping;
import java.util.List;

public interface ShippingDao extends BaseXCloudDao<Shipping, Integer> {

  List<Shipping> findAllByUserId(Integer userId);

  Shipping deleteShippingByIdAndUserId(Integer id, Integer userId);

  Shipping findTopByIdAndUserId(Integer id, Integer userId);

}