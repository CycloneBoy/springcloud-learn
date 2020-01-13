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
//    Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
    Order order = null;
    if (order == null) {
      return ServerResponse.createByErrorMessage("用户没有该订单");
    }
    resultMap.put("orderNo", String.valueOf(order.getOrderNo()));

    // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
    // 需保证商户系统端不能重复，建议通过数据库sequence生成，
    String outTradeNo = order.getOrderNo().toString();

    // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
    String subject = new StringBuilder().append("happymmall扫码支付,订单号:").append(outTradeNo)
        .toString();

    // (必填) 订单总金额，单位为元，不能超过1亿元
    // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
    String totalAmount = order.getPayment().toString();

    // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
    // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
    String undiscountableAmount = "0";

    // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
    // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
    String sellerId = "";

    // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
    String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共")
        .append(totalAmount).append("元").toString();

    // 商户操作员编号，添加此参数可以为商户操作员做销售统计
    String operatorId = "test_operator_id";

    // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
    String storeId = "test_store_id";

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
