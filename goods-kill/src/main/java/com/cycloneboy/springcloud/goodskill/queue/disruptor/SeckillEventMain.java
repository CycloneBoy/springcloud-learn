package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.concurrent.ThreadFactory;

/**
 * 测试类 Create by  sl on 2019-12-12 14:36
 */
public class SeckillEventMain {

  public static void main(String[] args) {
    producerWithTranslator();
  }

  public static void producerWithTranslator() {
    int ringBufferSize = 1024;

    SeckillEventFactory seckillEventFactory = new SeckillEventFactory();

    ThreadFactory threadFactory = Thread::new;

    //创建disruptor
    Disruptor<SeckillEvent> disruptor = new Disruptor<>(seckillEventFactory,
        ringBufferSize, threadFactory);

    //连接消费事件方法
    disruptor.handleEventsWith(new SeckillEventConsumer());

    //启动
    disruptor.start();

    RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();

    SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);

    for (int i = 0; i < 10; i++) {
      producer.seckill(i, i);
    }

    //关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
    disruptor.shutdown();
  }
}
