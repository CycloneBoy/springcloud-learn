package com.cycloneboy.springcloud.kafkahello.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Create by  sl on 2019-08-25 16:41
 */
public interface SinkSender {

  String OUTPUT = "TestInput";

  @Output(OUTPUT)
  MessageChannel output();
}
