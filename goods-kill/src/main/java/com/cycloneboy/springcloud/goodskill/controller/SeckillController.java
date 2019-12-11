package com.cycloneboy.springcloud.goodskill.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Create by  sl on 2019-12-11 11:59
 */
@Slf4j
@Api(tags = "秒杀")
@RestController
@RequestMapping("/seckill")
public class SeckillController {

  private static int corePoolSize = Runtime.getRuntime().availableProcessors();

  //创建线程池  调整队列数 拒绝服务
  private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
      corePoolSize + 1, 10l,
      TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));


  @Autowired
  private SeckillService seckillService;

  private String resultMessage;

  /**
   * 秒杀一(最low实现)
   * <p>
   * 最简单的查询更新操作，不涉及各种锁，会出现超卖情况。
   * <p>
   * 一共卖出108件商品
   *
   * @param seckillId
   * @return
   */
  @ApiOperation(value = "秒杀一(最low实现)")
  @PostMapping("/start")
  public BaseResponse start(long seckillId) {
    int skillNum = 1000;
    //N个购买者
    final CountDownLatch latch = new CountDownLatch(skillNum);

    seckillService.deleteSeckill(seckillId);
    final long killId = seckillId;
    log.info("开始秒杀一(会出现超卖)");

    /**
     * 开启新线程之前，将RequestAttributes对象设置为子线程共享
     * 这里仅仅是为了测试，否则 IPUtils 中获取不到 request 对象
     * 用到限流注解的测试用例，都需要加一下两行代码
     */
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    RequestContextHolder.setRequestAttributes(sra, true);

    for (int i = 0; i < skillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        BaseResponse response = seckillService.startSeckill(seckillId, userId);
        log.info("用户:{} {}-{}", userId, response.getCode(), response.getMessage());
        latch.countDown();
      };
      executor.execute(task);
    }

    try {
      // 等待所有人任务结束
      latch.await();
      Long seckillCount = seckillService.getSeckillCount(seckillId);
      resultMessage = "一共秒杀出 " + seckillCount + " 件商品";
      log.info(resultMessage);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return BaseResponse.ok(resultMessage);
  }

  /**
   * 秒杀二(程序锁)
   * <p>
   * 使用ReentrantLock重入锁，由于事物提交和锁释放的先后顺序也会导致超卖
   * <p>
   * 卖出一共秒杀出102件商品
   *
   * @param seckillId
   * @return
   */
  @ApiOperation(value = "秒杀二(程序锁)")
  @PostMapping("/startLock")
  public BaseResponse startLock(long seckillId) {
    int skillNum = 1000;
    //N个购买者
    final CountDownLatch latch = new CountDownLatch(skillNum);

    seckillService.deleteSeckill(seckillId);
    final long killId = seckillId;
    log.info("开始秒杀二正常(程序锁)");

    for (int i = 0; i < skillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        BaseResponse response = seckillService.startSeckillLock(seckillId, userId);
        log.info("用户:{} {}-{}", userId, response.getCode(), response.getMessage());
        latch.countDown();
      };
      executor.execute(task);
    }

    try {
      // 等待所有人任务结束
      latch.await();
      Long seckillCount = seckillService.getSeckillCount(seckillId);
      resultMessage = "一共秒杀出 " + seckillCount + " 件商品";
      log.info(resultMessage);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return BaseResponse.ok(resultMessage);
  }

  /**
   * 秒杀二(程序锁)
   * <p>
   * 使用ReentrantLock重入锁，由于事物提交和锁释放的先后顺序也会导致超卖
   * <p>
   * 卖出一共秒杀出102件商品
   *
   * @param seckillId
   * @return
   */
  @ApiOperation(value = "秒杀三(AOP程序锁)")
  @PostMapping("/startAopLock")
  public BaseResponse startAopLock(long seckillId) {
    int skillNum = 1000;
    //N个购买者
    final CountDownLatch latch = new CountDownLatch(skillNum);

    seckillService.deleteSeckill(seckillId);
    final long killId = seckillId;
    log.info("开始秒杀三(AOP程序锁)");

    for (int i = 0; i < skillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        BaseResponse response = seckillService.startSeckillAopLock(seckillId, userId);
        log.info("用户:{} {}-{}", userId, response.getCode(), response.getMessage());
        latch.countDown();
      };
      executor.execute(task);
    }

    try {
      // 等待所有人任务结束
      latch.await();
      Long seckillCount = seckillService.getSeckillCount(seckillId);
      resultMessage = "一共秒杀出 " + seckillCount + " 件商品";
      log.info(resultMessage);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return BaseResponse.ok(resultMessage);
  }

}
