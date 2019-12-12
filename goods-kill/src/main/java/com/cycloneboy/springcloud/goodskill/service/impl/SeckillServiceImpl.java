package com.cycloneboy.springcloud.goodskill.service.impl;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.common.Constants;
import com.cycloneboy.springcloud.goodskill.common.aop.ServiceLimit;
import com.cycloneboy.springcloud.goodskill.common.aop.ServiceLimit.LimitType;
import com.cycloneboy.springcloud.goodskill.common.aop.Servicelock;
import com.cycloneboy.springcloud.goodskill.common.dynamicquery.DynamicQuery;
import com.cycloneboy.springcloud.goodskill.common.enums.SeckillStatEnum;
import com.cycloneboy.springcloud.goodskill.entity.Seckill;
import com.cycloneboy.springcloud.goodskill.entity.SuccessKilled;
import com.cycloneboy.springcloud.goodskill.repository.SeckillRepository;
import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by  sl on 2019-12-10 17:43
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

  /**
   * 思考：为什么不用synchronized service 默认是单例的，并发下lock只有一个实例
   */
  //互斥锁 参数默认false，不公平锁
  private Lock lock = new ReentrantLock(true);

  @Autowired
  private DynamicQuery dynamicQuery;


  @Autowired
  private SeckillRepository seckillRepository;

  /**
   * 查询全部的秒杀记录
   *
   * @return
   */
  @Override
  public List<Seckill> getSeckillList() {
    return seckillRepository.findAll();
  }

  /**
   * 查询单个秒杀记录
   *
   * @param seckillId
   * @return
   */
  @Override
  public Seckill getById(long seckillId) {
    return seckillRepository.findById(seckillId).orElse(new Seckill());
  }

  /**
   * 查询秒杀售卖商品
   *
   * @param seckillId
   * @return
   */
  @Override
  public Long getSeckillCount(long seckillId) {
    String nativeSql =
        "select count(*) from " + Constants.TABLE_NAME_SUCCESS_KILLED + " where seckill_id=?";
    Object object = dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
    return ((Number) object).longValue();
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
   * 减少库存,售卖一件商品
   *
   * @param seckillId 商品ID
   */
  @Transactional
  public void subStock(long seckillId, Integer number) {
    // 扣库存
    String nativeSql = "update " + Constants.TABLE_NAME_SECKILL + " set number=number-" + number
        + " where seckill_id=?";
    dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
  }

  /**
   * 减少库存,售卖一件商品, 减少库存要查看库存数量是否大于零
   *
   * 乐观锁
   *
   * @param seckillId 商品ID
   */
  @Transactional
  public Integer subStockAndCheckVersion(long seckillId, Long number, Integer version) {
    // 扣库存
    //乐观锁
    String nativeSql = "update " + Constants.TABLE_NAME_SECKILL + " set number=number- ?" +
        " ,version=version+1 where seckill_id=? and version = ?";
    int count = dynamicQuery
        .nativeExecuteUpdate(nativeSql, new Object[]{number, seckillId, version});
    return count;
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
   * 查询库存库存,上悲观锁
   *
   * @param seckillId
   * @return 库存
   */
  @Transactional
  public Long checkStockForUpdate(long seckillId) {
    //校验库存
    String nativeSql =
        "select number from " + Constants.TABLE_NAME_SECKILL + " where seckill_id=? for update";
    Object object = dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
    Long number = ((Number) object).longValue();
    return number;
  }


  /**
   * 删除秒杀售卖商品记录
   *
   * @param seckillId
   * @return
   */
  @Override
  @Transactional
  public void deleteSeckill(long seckillId) {
    String nativeSql = "delete from " + Constants.TABLE_NAME_SUCCESS_KILLED + " where seckill_id=?";
    dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
    nativeSql = "update " + Constants.TABLE_NAME_SECKILL + " set number= "
        + Constants.SECKILL_DEFAULT_NUMBER + " where seckill_id=?";
    dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
  }

  /**
   * 秒杀 一、会出现数量错误
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
//  @ServiceLimit(limitType = LimitType.IP)
  @Transactional
  public BaseResponse startSeckill(long seckillId, long userid) {
    //校验库存
    Long number = checkStock(seckillId);
    if (number <= 0) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    subStock(seckillId, 1);
    saveOrder(seckillId, userid);

    //支付
    return new BaseResponse(SeckillStatEnum.SUCCESS);
  }


  /**
   * 秒杀 二、程序锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  @Transactional
  public BaseResponse startSeckillLock(long seckillId, long userid) {

    /**
     * 1)这里、不清楚为啥、总是会被超卖101、难道锁不起作用、lock是同一个对象
     * 2)来自热心网友 zoain 的细心测试思考、然后自己总结了一下,事物未提交之前，锁已经释放(事物提交是在整个方法执行完)，导致另一个事物读取到了这个事物未提交的数据，也就是传说中的脏读。建议锁上移
     * 3)给自己留个坑思考：为什么分布式锁(zk和redis)没有问题？(事实是有问题的，由于redis释放锁需要远程通信，不那么明显而已)
     * 4)2018年12月35日，更正一下,之前的解释（脏读）可能给大家一些误导,数据库默认的事务隔离级别为 可重复读(repeatable-read)，也就不可能出现脏读
     * 哪个这个级别是只能是幻读了？分析一下：幻读侧重于新增或删除，这里显然不是，那这里到底是什么，给各位大婶留个坑~~~~
     */
    try {
      lock.lock();
      Long number = checkStock(seckillId);
      if (number <= 0) {
        return new BaseResponse(SeckillStatEnum.END);
      }

      // 减少库存
      subStock(seckillId, 1);
      // 保存订单
      saveOrder(seckillId, userid);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return new BaseResponse(SeckillStatEnum.SUCCESS);
  }


  /**
   * 秒杀 三、使用AOP + 锁实现
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  @Servicelock
  @Transactional
  public BaseResponse startSeckillAopLock(long seckillId, long userid) {
    //来自码云码友<马丁的早晨>的建议 使用AOP + 锁实现
    // 检查库存
    Long number = checkStock(seckillId);
    if (number <= 0) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    // 减少库存
    subStock(seckillId, 1);
    // 保存订单
    saveOrder(seckillId, userid);

    return new BaseResponse(SeckillStatEnum.SUCCESS);
  }

  /**
   * 秒杀 四、数据库悲观锁
   * 注意这里 限流注解 可能会出现少买 自行调整
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  @ServiceLimit(limitType = LimitType.IP)
  @Transactional
  public BaseResponse startSeckillDbpccOne(long seckillId, long userid) {
    //单用户抢购一件商品或者多件都没有问题
    // SELECT number FROM seckill WHERE seckill_id=? FOR UPDATE
    //校验库存
    Long number = checkStockForUpdate(seckillId);
    if (number <= 0) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    subStock(seckillId, 1);
    saveOrder(seckillId, userid);

    //支付
    return new BaseResponse(SeckillStatEnum.SUCCESS);

  }

  /**
   * 秒杀 五、数据库悲观锁
   *
   * SHOW STATUS LIKE 'innodb_row_lock%';
   * 如果发现锁争用比较严重，如InnoDB_row_lock_waits和InnoDB_row_lock_time_avg的值比较高
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  @Transactional
  public BaseResponse startSeckillDbpccTwo(long seckillId, long userid) {
    //单用户抢购一件商品没有问题、但是抢购多件商品不建议这种写法
    // UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0

    Integer count = subStockAndCheck(seckillId, 1);
    if (count <= 0) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    saveOrder(seckillId, userid);

    //支付
    return new BaseResponse(SeckillStatEnum.SUCCESS);
  }

  /**
   * 秒杀 三、数据库乐观锁
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  @Transactional
  public BaseResponse startSeckillDbocc(long seckillId, long userid, long number) {
    Seckill seckill = getById(seckillId);
//    log.info("seckill:{}", seckill.toString());
    //剩余的数量应该要大于等于秒杀的数量
    if (seckill.getNumber() < number) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    //乐观锁
    Integer count = subStockAndCheckVersion(seckillId, number, seckill.getVersion());
    if (count <= 0) {
      return new BaseResponse(SeckillStatEnum.END);
    }

    saveOrder(seckillId, userid);

    //支付
    return new BaseResponse(SeckillStatEnum.SUCCESS);

  }

  /**
   * 秒杀 四、事物模板
   *
   * @param seckillId
   * @param userid
   * @return
   */
  @Override
  public BaseResponse startSeckillTemplate(long seckillId, long userid, long number) {
    return null;
  }
}
