package com.cycloneboy.springcloud.slmall.module.mmall.service;


import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Cart;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.CartVo;

/**
 * Created by geely
 */
public interface ICartService extends BaseXCloudService<Cart, Integer> {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
