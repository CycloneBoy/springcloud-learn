package com.cycloneboy.springcloud.goodskill.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.common.redis.RedisUtil;
import com.cycloneboy.springcloud.goodskill.service.SeckillDistributedService;
import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-12-13 16:59
 */
@Slf4j
@Api(tags = "分布式秒杀")
@RestController
@RequestMapping("/seckillDistributed")
public class SeckillDistributedController {

  private static int corePoolSize = Runtime.getRuntime().availableProcessors();

  //创建线程池  调整队列数 拒绝服务
  private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
      corePoolSize + 1, 10l,
      TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000));


  @Autowired
  private SeckillService seckillService;

  @Autowired
  private SeckillDistributedService seckillDistributedService;

  @Autowired
  private RedisUtil redisUtil;


  private String resultMessage;


  /**
   * 秒杀一(Rediss分布式锁)
   * <p>
   * 使用ReentrantLock重入锁，由于事物提交和锁释放的先后顺序也会导致超卖
   * <p>
   * 卖出一共秒杀出102件商品
   *
   * @param seckillId
   * @return
   */
  @ApiOperation(value = "秒杀一(Rediss分布式锁)")
  @PostMapping("/startRedisLock")
  public BaseResponse startRedisLock(long seckillId) {
    int skillNum = 1000;
    //N个购买者
    final CountDownLatch latch = new CountDownLatch(skillNum);

    seckillService.deleteSeckill(seckillId);
    final long killId = seckillId;
    log.info("秒杀一(Rediss分布式锁)");

    for (int i = 0; i < skillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        BaseResponse response = seckillDistributedService.startSeckillRedisLock(seckillId, userId);
        log.info("用户:{} {}-{}", userId, response.getCode(), response.getMessage());
        latch.countDown();
      };
      executor.execute(task);
    }

    try {
      // 等待所有人任务结束
      latch.await();
      Thread.sleep(5000);
      Long seckillCount = seckillService.getSeckillCount(seckillId);
      resultMessage = "一共秒杀出 " + seckillCount + " 件商品";
      log.info(resultMessage);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return BaseResponse.ok(resultMessage);
  }


}
