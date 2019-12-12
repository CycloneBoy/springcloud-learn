package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 事件生成工厂（用来初始化预分配事件对象）
 * <p>
 * Create by  sl on 2019-12-12 14:36
 */
public class SeckillEventFactory implements EventFactory<SeckillEvent> {

  @Override
  public SeckillEvent newInstance() {
    return new SeckillEvent();
  }
}
