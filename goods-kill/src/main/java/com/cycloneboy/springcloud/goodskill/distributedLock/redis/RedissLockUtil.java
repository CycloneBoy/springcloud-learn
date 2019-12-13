package com.cycloneboy.springcloud.goodskill.distributedLock.redis;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * Create by  sl on 2019-12-13 17:26
 */
public class RedissLockUtil {

  private static RedissonClient redissonClient;

  public void setRedissonClient(RedissonClient locker) {
    redissonClient = locker;
  }

  /**
   * 加锁
   *
   * @param lockKey 锁名称
   * @return
   */
  public static RLock lock(String lockKey) {
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock();
    return lock;
  }

  /**
   * 带超时的锁
   *
   * @param lockKey 锁名称
   * @param timeout 超时时间 单位:秒
   * @return
   */
  public static RLock lock(String lockKey, int timeout) {
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock(timeout, TimeUnit.SECONDS);
    return lock;
  }

  /**
   * 带超时的锁
   *
   * @param lockKey 锁名称
   * @param timeout 超时时间
   * @param unit    时间单位
   * @return
   */
  public static RLock lock(String lockKey, int timeout, TimeUnit unit) {
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock(timeout, unit);
    return lock;
  }

  /**
   * 尝试获取锁
   *
   * @param lockKey   锁名称
   * @param waitTime  最多等待时间
   * @param leaseTime 最多等待时间
   * @return
   */
  public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
    RLock lock = redissonClient.getLock(lockKey);
    try {
      return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 尝试获取锁
   *
   * @param lockKey   锁名称
   * @param waitTime  最多等待时间
   * @param leaseTime 最多等待时间
   * @param unit      时间单位
   * @return
   */
  public static boolean tryLock(String lockKey, int waitTime, int leaseTime, TimeUnit unit) {
    RLock lock = redissonClient.getLock(lockKey);
    try {
      return lock.tryLock(waitTime, leaseTime, unit);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 释放锁
   *
   * @param lockKey 锁名称
   */
  public static void unLock(String lockKey) {
    RLock lock = redissonClient.getLock(lockKey);
    lock.unlock();
  }


}
