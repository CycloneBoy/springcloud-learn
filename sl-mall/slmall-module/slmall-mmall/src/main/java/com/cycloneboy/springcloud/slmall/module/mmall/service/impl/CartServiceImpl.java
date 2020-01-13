package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.Const;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ResponseCode;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CartDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Cart;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICartService;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.BigDecimalUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.PropertiesUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.CartProductVo;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by  sl on 2020-01-12 17:50
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

  @Autowired
  private CartDao cartDao;

  @Autowired
  private IProductService productService;

  @Override
  public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
    if (productId == null || count == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }

    Cart cart = cartDao.findTopByUserIdAndProductId(userId, productId);
    if (cart == null) {
      //这个产品不在这个购物车里,需要新增一个这个产品的记录
      Cart cartItem = new Cart();
      cartItem.setQuantity(count);
      cartItem.setChecked(Const.Cart.CHECKED);
      cartItem.setProductId(productId);
      cartItem.setUserId(userId);
      cartItem.setCreateTime(new Date());
      cartItem.setUpdateTime(cartItem.getCreateTime());
      save(cartItem);
    } else {
      //这个产品已经在购物车里了.
      //如果产品已存在,数量相加
      count = cart.getQuantity() + count;
      cart.setQuantity(count);
      cart.setUpdateTime(new Date());
      save(cart);
    }
    return this.list(userId);
  }

  @Override
  public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
    if (productId == null || count == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }
    Cart cart = cartDao.findTopByUserIdAndProductId(userId, productId);
    if (cart == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }
    cart.setQuantity(count);
    cart.setUpdateTime(new Date());

    save(cart);
    return this.list(userId);
  }

  @Override
  @Transactional
  public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {

    List<String> productList = Splitter.on(",").splitToList(productIds);
    if (CollectionUtils.isEmpty(productList)) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }
    List<Integer> idList = new ArrayList<>();
    productList.forEach(id -> idList.add(Integer.parseInt(id)));
    cartDao.deleteAllByUserIdAndProductIdIn(userId, idList);
    return this.list(userId);
  }

  @Override
  public ServerResponse<CartVo> list(Integer userId) {
    CartVo cartVo = this.getCartVoLimit(userId);
    return ServerResponse.createBySuccess(cartVo);
  }

  @Override
  public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId,
      Integer checked) {

    if (productId != null) {
      Cart cart = cartDao.findTopByUserIdAndProductId(userId, productId);
      cart.setChecked(checked);
      cart.setUpdateTime(new Date());
      cartDao.save(cart);
    } else {
      // 选中所有
      List<Cart> cartList = cartDao.findAllByUserId(userId);
      cartList.forEach(cart -> {
        cart.setChecked(checked);
        cart.setUpdateTime(new Date());
      });

      cartDao.saveAll(cartList);

    }

    return this.list(userId);
  }

  @Override
  public ServerResponse<Integer> getCartProductCount(Integer userId) {
    if (userId == null) {
      return ServerResponse.createBySuccess(0);
    }

    List<Cart> cartList = cartDao.findAllByUserId(userId);
    AtomicInteger sumOfCart = new AtomicInteger(0);

    cartList
        .forEach(cart -> sumOfCart.addAndGet(cart.getQuantity() != null ? cart.getQuantity() : 0));

    return ServerResponse.createBySuccess(sumOfCart.get());
  }

  @Override
  public BaseXCloudDao<Cart, Integer> getRepository() {
    return cartDao;
  }


  private CartVo getCartVoLimit(Integer userId) {
    CartVo cartVo = new CartVo();
    List<Cart> cartList = cartDao.findAllByUserId(userId);
    List<CartProductVo> cartProductVoList = Lists.newArrayList();

    BigDecimal cartTotalPrice = new BigDecimal("0");

    if (CollectionUtils.isNotEmpty(cartList)) {
      for (Cart cartItem : cartList) {
        CartProductVo cartProductVo = new CartProductVo();
        cartProductVo.setId(cartItem.getId());
        cartProductVo.setUserId(userId);
        cartProductVo.setProductId(cartItem.getProductId());

        Product product = productService.get(cartItem.getProductId()).orElse(null);
        if (product != null) {
          cartProductVo.setProductMainImage(product.getMainImage());
          cartProductVo.setProductName(product.getName());
          cartProductVo.setProductSubtitle(product.getSubtitle());
          cartProductVo.setProductStatus(product.getStatus());
          cartProductVo.setProductPrice(product.getPrice());
          cartProductVo.setProductStock(product.getStock());
          //判断库存
          int buyLimitCount = 0;
          if (product.getStock() >= cartItem.getQuantity()) {
            //库存充足的时候
            buyLimitCount = cartItem.getQuantity();
            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
          } else {
            buyLimitCount = product.getStock();
            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
            //购物车中更新有效库存

            cartItem.setQuantity(buyLimitCount);
            cartDao.save(cartItem);
          }
          cartProductVo.setQuantity(buyLimitCount);
          //计算总价
          cartProductVo.setProductTotalPrice(
              BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
          cartProductVo.setProductChecked(cartItem.getChecked());
        }

        if (cartItem.getChecked() == Const.Cart.CHECKED) {
          //如果已经勾选,增加到整个的购物车总价中
          cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
              cartProductVo.getProductTotalPrice().doubleValue());
        }
        cartProductVoList.add(cartProductVo);
      }
    }

    cartVo.setCartTotalPrice(cartTotalPrice);
    cartVo.setCartProductVoList(cartProductVoList);
    cartVo.setAllChecked(this.getAllCheckedStatus(userId));
    cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

    return cartVo;
  }

  private boolean getAllCheckedStatus(Integer userId) {
    if (userId == null) {
      return false;
    }
    return cartDao.countByUserIdAndChecked(userId, Const.Cart.UN_CHECKED) == 0;

  }
}
