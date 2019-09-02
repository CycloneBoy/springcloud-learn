package com.cycloneboy.springcloud.common.kafka;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_AUTHOR_OUTPUT;
import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_AUTHOR_SAVE;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Create by  sl on 2019-08-25 23:49
 */
public interface TravelAuthorStreams {

  @Input(KAFKA_SENDER_TRAVEL_AUTHOR_OUTPUT)
  SubscribableChannel receive();

  @Output(KAFKA_SENDER_TRAVEL_AUTHOR_SAVE)
  MessageChannel send();
}
