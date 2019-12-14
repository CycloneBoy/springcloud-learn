package com.cycloneboy.springcloud.goodskill.common;

/**
 * Create by  sl on 2019-12-10 20:41
 */
public class Constants {

  public static final String GOODS_TEMPLATE_NAME = "goods.flt";

  public static final String SUCCESS = "success";

  public static final String TABLE_NAME_SUCCESS_KILLED = "success_killed";

  public static final String TABLE_NAME_SECKILL = "seckill";

  /**
   * 设置秒杀商品的默认库存
   */
  public static final int SECKILL_DEFAULT_NUMBER = 100;

  /**
   * 商品秒杀成功\失败的状态
   */
  public static final short SUCCESS_KILLED_STAT_SUCCESS = 0;

  public static final short SUCCESS_KILLED_STAT_FAILED = 1;

  /**
   * 并发访问的次数
   */
  public static final int THREAD_SIZE = 1000;

  /**
   * redis 前缀
   */
  public static final String KEY_PREFIX_VALUE = "cycloneboy:seckill:value:";

  public static final String ZK_ADDRESS = "127.0.0.1:2181";

}
