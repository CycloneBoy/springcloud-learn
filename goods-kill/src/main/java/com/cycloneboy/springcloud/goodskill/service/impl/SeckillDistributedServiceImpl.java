package com.cycloneboy.springcloud.goodskill.service.impl;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.common.Constants;
import com.cycloneboy.springcloud.goodskill.common.dynamicquery.DynamicQuery;
import com.cycloneboy.springcloud.goodskill.common.enums.SeckillStatEnum;
import com.cycloneboy.springcloud.goodskill.distributedLock.redis.RedissLockUtil;
import com.cycloneboy.springcloud.goodskill.entity.SuccessKilled;
import com.cycloneboy.springcloud.goodskill.service.SeckillDistributedService;
import java.sql.Timestamp;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by  sl on 2019-12-13 17:06
 */
@Slf4j
@Service
public class SeckillDistributedServiceImpl implements SeckillDistributedService {

  @Autowired
  private DynamicQuery dynamicQuery;


  /**
   * 查询库存库存
   *
   * @param seckillId
   * @return 库存
   */
  @Transactional
  public Long checkStock(long seckillId) {
    //校验库存
    String nativeSql = "select number from " + Constants.TABLE_NAME_SECKILL + " where seckill_id=?";
    Object object = dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
    Long number = ((Number) object).longValue();
    return number;
  }


  /**
   * 保存订单,成功秒杀一件商品
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Transactional
  public void saveOrder(long seckillId, long userid) {
    String nativeSql;//创建订单
    SuccessKilled successKilled = new SuccessKilled();
    successKilled.setSeckillId(seckillId);
    successKilled.setUserId(userid);
    successKilled.setState(Constants.SUCCESS_KILLED_STAT_SUCCESS);
    Timestamp createTime = new Timestamp(new Date().getTime());
    successKilled.setCreateTime(createTime);
    // 此方法有问题,所以采用下面的方法保存
    //      dynamicQuery.save(successKilled);

    nativeSql =
        "insert into " + Constants.TABLE_NAME_SUCCESS_KILLED
            + " (seckill_id, user_id,state,create_time) VALUES(?,?,?,?)";
    Object[] params = new Object[]{seckillId, userid, Constants.SUCCESS_KILLED_STAT_SUCCESS,
        createTime};
    dynamicQuery.nativeExecuteUpdate(nativeSql, params);

    /**
     * 这里仅仅是分表而已，提供一种思路，供参考，测试的时候自行建表
     * 按照用户 ID 来做 hash分散订单数据。
     * 要扩容的时候，为了减少迁移的数据量，一般扩容是以倍数的形式增加。
     * 比如原来是8个库，扩容的时候，就要增加到16个库，再次扩容，就增加到32个库。
     * 这样迁移的数据量，就小很多了。
     * 这个问题不算很大问题，毕竟一次扩容，可以保证比较长的时间，而且使用倍数增加的方式，已经减少了数据迁移量。
     */
    String table = "success_killed_" + userid % 8;
    nativeSql =
        "insert into " + table + " (seckill_id, user_id,state,create_time) VALUES(?,?,?,?)";
    params = new Object[]{seckillId, userid, Constants.SUCCESS_KILLED_STAT_SUCCESS,
        createTime};
//      dynamicQuery.nativeExecuteUpdate(nativeSql, params);

  }

  /**
   * 减少库存,售卖一件商品, 减少库存要查看库存数量是否大于零
   *
   * @param seckillId 商品ID
   */
  @Transactional
  public Integer subStockAndCheck(long seckillId, Integer number) {
    // 扣库存
    String nativeSql = "update " + Constants.TABLE_NAME_SECKILL + " set number=number-" + number
        + " where seckill_id=? and number >0";
    int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
    return count;
  }

  /**
   * 秒杀 一  单个商品 基于redis 锁
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @return
   */
  @Override
  @Transactional
  public BaseResponse startSeckillRedisLock(long seckillId, long userId) {
    boolean result = false;
    /**
     * 尝试获取锁，最多等待3秒，上锁以后20秒自动解锁（实际项目中推荐这种，以防出现死锁）、这里根据预估秒杀人数，设定自动释放锁时间.
     * 看过博客的朋友可能会知道(Lock锁与事物冲突的问题)：https://blog.52itstyle.com/archives/2952/
     * 分布式锁的使用和Lock锁的实现方式是一样的，但是测试了多次分布式锁就是没有问题，当时就留了个坑
     * 闲来咨询了《静儿1986》，推荐下博客：https://www.cnblogs.com/xiexj/p/9119017.html
     * 先说明下之前的配置情况：Mysql在本地，而Redis是在外网。
     * 回复是这样的：
     * 这是因为分布式锁的开销是很大的。要和锁的服务器进行通信，它虽然是先发起了锁释放命令，涉及网络IO，延时肯定会远远大于方法结束后的事务提交。
     * ==========================================================================================
     * 分布式锁内部都是Runtime.exe命令调用外部，肯定是异步的。分布式锁的释放只是发了一个锁释放命令就算完活了。真正其作用的是下次获取锁的时候，要确保上次是释放了的。
     * 就是说获取锁的时候耗时比较长，那时候事务肯定提交了就是说获取锁的时候耗时比较长，那时候事务肯定提交了。
     * ==========================================================================================
     * 周末测试了一下，把redis配置在了本地，果然出现了超卖的情况；或者还是使用外网并发数增加在10000+也是会有问题的，之前自己没有细测，我的锅。
     * 所以这钟实现也是错误的，事物和锁会有冲突，建议AOP实现。
     */
    try {
      result = RedissLockUtil.tryLock(seckillId + "", 3, 10);
      if (!result) {
        return new BaseResponse(SeckillStatEnum.FAILED);
      }

      //校验库存
      Long number = checkStock(seckillId);
      if (number <= 0) {
        return new BaseResponse(SeckillStatEnum.END);
      }
      saveOrder(seckillId, userId);
      subStockAndCheck(seckillId, 1);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (result) {
        RedissLockUtil.unLock(seckillId + "");
      }
    }
    return new BaseResponse(SeckillStatEnum.SUCCESS);
  }

  /**
   * 秒杀 一  单个商品 基于zookeeper 锁
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @return
   */
  @Override
  public BaseResponse startSeckillZkLock(long seckillId, long userId) {
    return null;
  }

  /**
   * 秒杀 二 多个商品
   *
   * @param seckillId 秒杀商品ID
   * @param userId    用户ID
   * @param number    秒杀商品数量
   * @return
   */
  @Override
  public BaseResponse startSeckillLock(long seckillId, long userId, long number) {
    return null;
  }
}
