package com.cycloneboy.springcloud.goodskill.distributedLock.zookeeper;

import static com.cycloneboy.springcloud.goodskill.common.Constants.ZK_ADDRESS;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-12-14 20:30
 */
@Slf4j
@Component
public class ZkLockUtil {

  public static CuratorFramework client;

  static {
    //1、重试策略：初试时间为1s 重试3次
    ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
    //2、通过工厂创建连接
    CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, retryPolicy);
    //3、开启连接
    client.start();
    log.info("zookeeper 分布式锁启动");
  }

  public ZkLockUtil() {

  }

  private static class SingletonHolder {

    /**
     * 静态初始化器，由JVM来保证线程安全 参考：http://ifeve.com/zookeeper-lock/ 这里建议 new 一个
     */
    private static InterProcessMutex mutex = new InterProcessMutex(client, "/curator/zklock");
  }

  private static InterProcessMutex getMutex() {
    return SingletonHolder.mutex;
  }

  //获得了锁
  public static boolean acquire(long time, TimeUnit unit) {
    try {
      return getMutex().acquire(time, unit);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //释放锁
  public static void release() {
    try {
      getMutex().release();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
