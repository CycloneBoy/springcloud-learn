package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.Const;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CartDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.OrderDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.OrderItemDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ProductDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ShippingDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Cart;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Order;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.OrderItem;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Shipping;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IOrderService;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IShippingService;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.BigDecimalUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.DateTimeUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.PropertiesUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.OrderItemVo;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.OrderProductVo;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.OrderVo;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.ShippingVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

  @Autowired
  private CartDao cartDao;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private ShippingDao shippingDao;

  @Autowired
  private OrderItemDao orderItemDao;


  @Autowired
  private IProductService productService;

  @Autowired
  private IShippingService shippingService;


  @Override
  public ServerResponse pay(Long orderNo, Integer userId, String path) {
    Map<String, String> resultMap = Maps.newHashMap();
    Order order = orderDao.findTopByUserIdAndOrderNo(userId, orderNo);
    if (order == null) {
      return ServerResponse.createByErrorMessage("用户没有该订单");
    }
    resultMap.put("orderNo", String.valueOf(order.getOrderNo()));

    File folder = new File(path);
    if (!folder.exists()) {
      folder.setWritable(true);
      folder.mkdirs();
    }

    String qrPath = String.format(path + "/qr-%s.png", order.getOrderNo());
    String qrFileName = String.format("qr-%s.png", order.getOrderNo());
//    ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
    File targetFile = new File(path, qrFileName);

    log.info("qrPath:" + qrPath);
    String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
    resultMap.put("qrUrl", "http://localhost:5021/dist/resource/cycloneboy.jpeg");
    return ServerResponse.createBySuccess(resultMap);


  }

  @Override
  public ServerResponse aliCallback(Map<String, String> params) {
    return null;
  }

  @Override
  public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
    Order order = orderDao.findTopByUserIdAndOrderNo(userId, orderNo);
    if (order == null) {
      return ServerResponse.createByErrorMessage("用户没有该订单");
    }
    if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
      return ServerResponse.createBySuccess();
    }
    return ServerResponse.createByError();
  }

  @Override
  public ServerResponse createOrder(Integer userId, Integer shippingId) {
    //从购物车中获取数据
    List<Cart> cartList = cartDao.findAllByUserIdAndChecked(userId, Const.Cart.CHECKED);

    //计算这个订单的总价
    ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
    if (!serverResponse.isSuccess()) {
      return serverResponse;
    }
    List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
    BigDecimal payment = this.getOrderTotalPrice(orderItemList);

    //生成订单
    Order order = this.assembleOrder(userId, shippingId, payment);
    if (order == null) {
      return ServerResponse.createByErrorMessage("生成订单错误");
    }
    if (CollectionUtils.isEmpty(orderItemList)) {
      return ServerResponse.createByErrorMessage("购物车为空");
    }

    for (OrderItem orderItem : orderItemList) {
      orderItem.setOrderNo(order.getOrderNo());
    }
    //mybatis 批量插入
    orderItemDao.saveAll(orderItemList);

    //生成成功,我们要减少我们产品的库存
    this.reduceProductStock(orderItemList);
    //清空一下购物车
    this.cleanCart(cartList);

    //返回给前端数据

    OrderVo orderVo = assembleOrderVo(order, orderItemList);
    return ServerResponse.createBySuccess(orderVo);
  }

  private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(order.getOrderNo());
    orderVo.setPayment(order.getPayment());
    orderVo.setPaymentType(order.getPaymentType());
    orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

    orderVo.setPostage(order.getPostage());
    orderVo.setStatus(order.getStatus());
    orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

    orderVo.setShippingId(order.getShippingId());
    Shipping shipping = shippingService.get(order.getShippingId()).orElse(null);
    if (shipping != null) {
      orderVo.setReceiverName(shipping.getReceiverName());
      orderVo.setShippingVo(assembleShippingVo(shipping));
    }

    orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
    orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
    orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
    orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
    orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

    orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

    List<OrderItemVo> orderItemVoList = Lists.newArrayList();

    for (OrderItem orderItem : orderItemList) {
      OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
      orderItemVoList.add(orderItemVo);
    }
    orderVo.setOrderItemVoList(orderItemVoList);
    return orderVo;
  }

  private ShippingVo assembleShippingVo(Shipping shipping) {
    ShippingVo shippingVo = new ShippingVo();
    BeanUtils.copyProperties(shipping, shippingVo);
    shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
    return shippingVo;
  }

  private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
    BigDecimal payment = new BigDecimal("0");
    for (OrderItem orderItem : orderItemList) {
      payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
    }
    return payment;
  }

  private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
    Order order = new Order();
    long orderNo = this.generateOrderNo();
    order.setOrderNo(orderNo);
    order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
    order.setPostage(0);
    order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
    order.setPayment(payment);

    order.setUserId(userId);
    order.setShippingId(shippingId);

    order.setCreateTime(new Date());
    order.setUpdateTime(order.getCreateTime());
    //发货时间等等
    //付款时间等等
    Order saveOrder = save(order);

    return saveOrder;
  }

  private long generateOrderNo() {
    long currentTime = System.currentTimeMillis();
    return currentTime + new Random().nextInt(100);
  }


  private void cleanCart(List<Cart> cartList) {
    for (Cart cart : cartList) {
      cartDao.deleteById(cart.getId());
    }
  }


  /**
   * 减库存
   *
   * @param orderItemList
   */
  private void reduceProductStock(List<OrderItem> orderItemList) {
    for (OrderItem orderItem : orderItemList) {
      Product product = productService.get(orderItem.getProductId()).orElse(null);
      if (product != null) {
        product.setStock(product.getStock() - orderItem.getQuantity());
        product.setUpdateTime(new Date());
      }

      productService.save(product);
    }
  }

  @Override
  public ServerResponse<String> cancel(Integer userId, Long orderNo) {
    return null;
  }

  @Override
  public ServerResponse getOrderCartProduct(Integer userId) {
    OrderProductVo orderProductVo = new OrderProductVo();
    //从购物车中获取数据

    List<Cart> cartList = cartDao.findAllByUserIdAndChecked(userId, Const.Cart.CHECKED);
    ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
    if (!serverResponse.isSuccess()) {
      return serverResponse;
    }
    List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();

    List<OrderItemVo> orderItemVoList = Lists.newArrayList();

    BigDecimal payment = new BigDecimal("0");
    for (OrderItem orderItem : orderItemList) {
      payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
      orderItemVoList.add(assembleOrderItemVo(orderItem));
    }
    orderProductVo.setProductTotalPrice(payment);
    orderProductVo.setOrderItemVoList(orderItemVoList);
    orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
    return ServerResponse.createBySuccess(orderProductVo);
  }

  private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
    OrderItemVo orderItemVo = new OrderItemVo();
    BeanUtils.copyProperties(orderItem, orderItemVo);
    return orderItemVo;
  }

  private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
    List<OrderItem> orderItemList = Lists.newArrayList();
    if (CollectionUtils.isEmpty(cartList)) {
      return ServerResponse.createByErrorMessage("购物车为空");
    }

    //校验购物车的数据,包括产品的状态和数量
    for (Cart cartItem : cartList) {
      OrderItem orderItem = new OrderItem();
      Product product = productService.get(cartItem.getProductId()).orElse(null);
      if (product == null) {
        return ServerResponse.createByErrorMessage("产品不存在");
      }
      if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
        return ServerResponse.createByErrorMessage("产品" + product.getName() + "不是在线售卖状态");
      }

      //校验库存
      if (cartItem.getQuantity() > product.getStock()) {
        return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
      }

      orderItem.setUserId(userId);
      orderItem.setProductId(product.getId());
      orderItem.setProductName(product.getName());
      orderItem.setProductImage(product.getMainImage());
      orderItem.setCurrentUnitPrice(product.getPrice());
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setTotalPrice(
          BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));

      orderItem.setCreateTime(new Date());
      orderItem.setUpdateTime(orderItem.getCreateTime());
      orderItemList.add(orderItem);
    }
    return ServerResponse.createBySuccess(orderItemList);
  }

  @Override
  public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
    Order order = orderDao.findTopByUserIdAndOrderNo(userId, orderNo);
    if (order != null) {
      List<OrderItem> orderItemList = orderItemDao.findAllByOrderNoAndUserId(orderNo, userId);
      OrderVo orderVo = assembleOrderVo(order, orderItemList);
      return ServerResponse.createBySuccess(orderVo);
    }
    return ServerResponse.createByErrorMessage("没有找到该订单");
  }

  @Override
  public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
    List<Order> orderList = orderDao.findAllByUserId(userId);
    List<OrderVo> orderVoList = assembleOrderVoList(orderList, userId);
    PageInfo pageResult = new PageInfo(orderList);
    pageResult.setList(orderVoList);
    return ServerResponse.createBySuccess(pageResult);
  }

  private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
    List<OrderVo> orderVoList = Lists.newArrayList();
    for (Order order : orderList) {
      List<OrderItem> orderItemList = Lists.newArrayList();

      if (userId == null) {
        //todo 管理员查询的时候 不需要传userId
        orderItemList = orderItemDao.findAllByOrderNo(order.getOrderNo());
      } else {
        orderItemList = orderItemDao.findAllByOrderNoAndUserId(order.getOrderNo(), userId);
      }

      OrderVo orderVo = assembleOrderVo(order, orderItemList);
      orderVoList.add(orderVo);
    }
    return orderVoList;
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
