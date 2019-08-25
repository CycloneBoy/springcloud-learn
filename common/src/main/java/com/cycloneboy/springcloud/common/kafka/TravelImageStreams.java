package com.cycloneboy.springcloud.common.kafka;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_IMAGE_INPUT;
import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Create by  sl on 2019-08-25 23:49
 */
public interface TravelImageStreams {

  @Input(KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT)
  SubscribableChannel receive();

  @Output(KAFKA_SENDER_TRAVEL_IMAGE_INPUT)
  MessageChannel send();
}
