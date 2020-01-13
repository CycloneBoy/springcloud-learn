package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Cart;
import java.util.List;

public interface CartDao extends BaseXCloudDao<Cart, Integer> {

  Cart findTopByUserIdAndProductId(Integer userId, Integer productId);

  List<Cart> findAllByUserId(Integer userId);

  Integer countByUserIdAndChecked(Integer userId, Integer checked);

  Integer deleteAllByUserIdAndProductIdIn(Integer userId, List<Integer> productIdList);

  List<Cart> findAllByUserIdAndChecked(Integer userId, Integer checked);
}