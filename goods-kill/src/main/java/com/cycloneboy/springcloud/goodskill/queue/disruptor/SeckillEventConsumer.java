package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import com.cycloneboy.springcloud.goodskill.common.config.SpringUtil;
import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-12-12 14:36
 */
@Slf4j
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {

  private SeckillService seckillService = (SeckillService) SpringUtil.getBean("seckillService");

  @Override
  public void onEvent(SeckillEvent seckillEvent, long l, boolean b) throws Exception {
    seckillService.startSeckill(seckillEvent.getSeckillId(), seckillEvent.getUserId());
    log.info("SeckillEventConsumer consumer:{} - {}", seckillEvent.getSeckillId(),
        seckillEvent.getUserId());
  }
}
