package com.cycloneboy.springcloud.goodskill.service;

import com.cycloneboy.springcloud.common.domain.BaseResponse;

/**
 * Create by  sl on 2019-12-13 17:03
 */
public interface SeckillDistributedService {

  /**
   * 秒杀 一  单个商品 基于redis 锁
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @return
   */
  BaseResponse startSeckillRedisLock(long seckillId, long userId);


  /**
   * 秒杀 一  单个商品 基于zookeeper 锁
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @return
   */
  BaseResponse startSeckillZkLock(long seckillId, long userId);


  /**
   * 秒杀 二 多个商品
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @param number    秒杀商品数量
   * @return
   */
  BaseResponse startSeckillLock(long seckillId, long userId, long number);
}
