package com.cycloneboy.springcloud.goodskill.distributedLock.zookeeper;

import static com.cycloneboy.springcloud.goodskill.common.Constants.ZK_ADDRESS;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;


/**
 * Create by sl on 2019-12-14 20:30 *
 * 基于curator的zookeeper分布式锁 *
 * 这里我们开启5个线程，每个线程获取锁的最大等待时间为5秒，为了模拟具体业务场景，方法中设置4秒等待时间。 *
 * 开始执行main方法，通过ZooInspector监控/curator/lock下的节点如下图：
 */
@Slf4j
public class CuratorUtil {


  public static void main(String[] args) {
    //1、重试策略：初试时间为1s 重试3次
    ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000,3);
    //2、通过工厂创建连接
    CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, retryPolicy);
    //3、开启连接
    client.start();

    //读写锁
    //InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/readwriter");
    final InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    for (int i = 0; i < 5; i++) {
      fixedThreadPool.submit(() -> {
        boolean flag = false;

        try {
          flag = mutex.acquire(5, TimeUnit.SECONDS);
          Thread currentThread = Thread.currentThread();
          if(flag){
            log.info("线程:{} 获取锁成功",currentThread.getId());
          }else {
            log.info("线程:{} 获取锁失败",currentThread.getId());
          }

          Thread.sleep(4000);

        } catch (Exception e) {
          e.printStackTrace();
        }finally{
          if(flag){
            try {
              mutex.release();
            } catch (Exception e) {
              e.printStackTrace();
            }

          }
        }
      });
    }
  }
}
