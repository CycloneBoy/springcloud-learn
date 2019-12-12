package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * Create by  sl on 2019-12-12 14:37
 */
public class SeckillEventProducer {

  private final RingBuffer<SeckillEvent> ringBuffer;

  private final static EventTranslatorVararg<SeckillEvent> translator = (event, sequence, args) -> {
    event.setSeckillId((Long) args[0]);
    event.setUserId((Long) args[1]);
  };

  public SeckillEventProducer(RingBuffer<SeckillEvent> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void seckill(long seckillId, long userId) {
    this.ringBuffer.publishEvent(translator, seckillId, userId);
  }
}
