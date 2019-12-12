package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.concurrent.ThreadFactory;

/**
 * 高性能队列——Disruptor: https://tech.meituan.com/2016/11/18/disruptor.html
 *
 * <p> Create by  sl on
 * 2019-12-12 13:24
 */
public class DisruptorUtil {

  private static Disruptor<SeckillEvent> disruptor;

  static {

    SeckillEventFactory factory = new SeckillEventFactory();
    int ringBufferSize = 1024;
    ThreadFactory threadFactory = Thread::new;

    //创建disruptor
    disruptor = new Disruptor<>(factory, ringBufferSize, threadFactory);

    //连接消费事件方法
    disruptor.handleEventsWith(new SeckillEventConsumer());

    //启动
    disruptor.start();
  }

  public static void producer(SeckillEvent seckillEvent) {
    RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();

    SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);

    producer.seckill(seckillEvent.getSeckillId(), seckillEvent.getUserId());
  }
}
