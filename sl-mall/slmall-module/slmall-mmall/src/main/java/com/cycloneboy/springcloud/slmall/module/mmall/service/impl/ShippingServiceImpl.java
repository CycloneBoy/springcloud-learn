package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ShippingDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Shipping;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 17:52
 */
@Slf4j
@Service
public class ShippingServiceImpl implements IShippingService {

  @Autowired
  private ShippingDao shippingDao;

  @Override
  public ServerResponse add(Integer userId, Shipping shipping) {
    return null;
  }

  @Override
  public ServerResponse<String> del(Integer userId, Integer shippingId) {
    return null;
  }

  @Override
  public ServerResponse update(Integer userId, Shipping shipping) {
    return null;
  }

  @Override
  public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
    return null;
  }

  @Override
  public BaseXCloudDao<Shipping, Integer> getRepository() {
    return shippingDao;
  }
}
