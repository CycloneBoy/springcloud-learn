package com.cycloneboy.springcloud.goodskill.queue.jvm;

import com.cycloneboy.springcloud.goodskill.common.enums.SeckillStatEnum;
import com.cycloneboy.springcloud.goodskill.entity.SuccessKilled;
import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-12-12 13:12
 */
@Slf4j
@Component
public class ConsumSeckillTaskRunner implements ApplicationRunner {

  @Autowired
  private SeckillService seckillService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    while (true) {
      SuccessKilled successKilled = SeckillQueue.getMailQueue().consume();
      if (successKilled != null) {
        seckillService.startSeckill(successKilled.getSeckillId(), successKilled.getUserId());
        log.info("ConsumSeckillTaskRunner 消费队列,用户:{} {}", successKilled.getUserId(),
            SeckillStatEnum.SUCCESS.getInfo());
      }
    }
  }
}
