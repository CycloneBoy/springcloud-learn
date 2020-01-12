package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CartDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Cart;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICartService;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 17:50
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

  @Autowired
  private CartDao cartDao;

  @Override
  public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
    return null;
  }

  @Override
  public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
    return null;
  }

  @Override
  public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
    return null;
  }

  @Override
  public ServerResponse<CartVo> list(Integer userId) {
    return null;
  }

  @Override
  public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId,
      Integer checked) {
    return null;
  }

  @Override
  public ServerResponse<Integer> getCartProductCount(Integer userId) {
    return null;
  }

  @Override
  public BaseXCloudDao<Cart, Integer> getRepository() {
    return cartDao;
  }
}
