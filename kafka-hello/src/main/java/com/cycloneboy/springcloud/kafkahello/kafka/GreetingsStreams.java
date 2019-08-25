package com.cycloneboy.springcloud.kafkahello.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Create by  sl on 2019-08-25 16:48
 */
public interface GreetingsStreams {

  String INPUT = "greetings-in";
  String OUTPUT = "greetings-out";

  @Input(INPUT)
  SubscribableChannel inboundGreetings();

  @Output(OUTPUT)
  MessageChannel outboundGreetings();

}
