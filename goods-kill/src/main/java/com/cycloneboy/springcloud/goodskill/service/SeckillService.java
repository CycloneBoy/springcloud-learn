package com.cycloneboy.springcloud.goodskill.service;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.entity.Seckill;
import java.util.List;

/**
 * Create by  sl on 2019-12-10 17:36
 */
public interface SeckillService {

  /**
   * 查询全部的秒杀记录
   *
   * @return
   */
  List<Seckill> getSeckillList();

  /**
   * 查询单个秒杀记录
   *
   * @param seckillId
   * @return
   */
  Seckill getById(long seckillId);

  /**
   * 查询秒杀售卖商品
   *
   * @param seckillId
   * @return
   */
  Long getSeckillCount(long seckillId);

  /**
   * 删除秒杀售卖商品记录
   *
   * @param seckillId
   * @return
   */
  void deleteSeckill(long seckillId);

  /**
   * 秒杀 一、会出现数量错误
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckill(long seckillId, long userid);

  /**
   * 秒杀 二、程序锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillLock(long seckillId, long userid);


  /**
   * 秒杀 二、程序锁AOP
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillAopLock(long seckillId, long userid);

  /**
   * 秒杀 二、数据库悲观锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillDbpccOne(long seckillId, long userid);

  /**
   * 秒杀 三、数据库悲观锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillDbpccTwo(long seckillId, long userid);

  /**
   * 秒杀 三、数据库乐观锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillDbocc(long seckillId, long userid);


  /**
   * 秒杀 四、事物模板
   *
   * @param seckillId
   * @param userid
   * @return
   */
  BaseResponse startSeckillTemplate(long seckillId, long userid);
}
