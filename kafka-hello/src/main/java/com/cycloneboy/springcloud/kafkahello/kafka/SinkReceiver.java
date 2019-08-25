package com.cycloneboy.springcloud.kafkahello.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Create by  sl on 2019-08-25 16:23
 */
@Slf4j
@EnableBinding(value = {Sink.class, SinkSender.class})
public class SinkReceiver {

  @StreamListener(SinkSender.OUTPUT)
  public void receive(Object payload) {
    log.info("receive:{}", payload);
  }

}
