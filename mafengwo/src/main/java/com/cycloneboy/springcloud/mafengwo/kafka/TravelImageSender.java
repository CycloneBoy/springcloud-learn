package com.cycloneboy.springcloud.mafengwo.kafka;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Create by  sl on 2019-08-25 23:36
 */
public interface TravelImageSender {


  @Output(KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT)
  MessageChannel output();

}
