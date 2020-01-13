package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ShippingDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Shipping;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IShippingService;
import com.google.common.collect.Maps;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    shipping.setUserId(userId);
    shipping.setCreateTime(new Date());
    shipping.setUpdateTime(shipping.getCreateTime());

    Shipping saveShipping = shippingDao.save(shipping);
    Map result = Maps.newHashMap();
    result.put("shippingId", saveShipping.getId());
    return ServerResponse.createBySuccess("新建地址成功", result);
  }

  @Override
  @Transactional
  public ServerResponse<String> del(Integer userId, Integer shippingId) {
    shippingDao.deleteById(shippingId);
    Shipping shipping = get(shippingId).orElse(null);
    if (shipping == null) {
      return ServerResponse.createBySuccess("删除地址成功");
    }
    return ServerResponse.createByErrorMessage("删除地址失败");
  }

  @Override
  public ServerResponse update(Integer userId, Shipping shipping) {
    shipping.setUserId(userId);
    shipping.setCreateTime(new Date());
    shipping.setUpdateTime(shipping.getCreateTime());

    Shipping saveShipping = shippingDao.save(shipping);

    if (saveShipping.getId().equals(shipping.getId())) {
      return ServerResponse.createBySuccess("更新地址成功");
    }
    return ServerResponse.createByErrorMessage("更新地址失败");
  }

  @Override
  public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
    Shipping shipping = shippingDao.findTopByIdAndUserId(shippingId, userId);
    if (shipping == null) {
      return ServerResponse.createByErrorMessage("无法查询到该地址");
    }
    return ServerResponse.createBySuccess("更新地址成功", shipping);
  }

  @Override
  public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {

    List<Shipping> shippingList = shippingDao.findAllByUserId(userId);
    PageInfo pageInfo = new PageInfo(shippingList);
    return ServerResponse.createBySuccess(pageInfo);
  }

  @Override
  public BaseXCloudDao<Shipping, Integer> getRepository() {
    return shippingDao;
  }
}
