package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.OrderDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Order;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IOrderService;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.OrderVo;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 17:51
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

  @Autowired
  private OrderDao orderDao;


  @Override
  public ServerResponse pay(Long orderNo, Integer userId, String path) {
    return null;
  }

  @Override
  public ServerResponse aliCallback(Map<String, String> params) {
    return null;
  }

  @Override
  public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
    return null;
  }

  @Override
  public ServerResponse createOrder(Integer userId, Integer shippingId) {
    return null;
  }

  @Override
  public ServerResponse<String> cancel(Integer userId, Long orderNo) {
    return null;
  }

  @Override
  public ServerResponse getOrderCartProduct(Integer userId) {
    return null;
  }

  @Override
  public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
    return null;
  }

  @Override
  public ServerResponse<OrderVo> manageDetail(Long orderNo) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
    return null;
  }

  @Override
  public ServerResponse<String> manageSendGoods(Long orderNo) {
    return null;
  }

  @Override
  public BaseXCloudDao<Order, Integer> getRepository() {
    return orderDao;
  }
}
