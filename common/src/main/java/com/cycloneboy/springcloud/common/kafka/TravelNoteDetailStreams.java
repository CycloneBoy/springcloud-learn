package com.cycloneboy.springcloud.common.kafka;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_NOTE_DETAIL_OUTPUT;
import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_NOTE_DETAIL_SAVE;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Create by  sl on 2019-08-25 23:49
 */
public interface TravelNoteDetailStreams {

  @Input(KAFKA_SENDER_TRAVEL_NOTE_DETAIL_OUTPUT)
  SubscribableChannel receive();

  @Output(KAFKA_SENDER_TRAVEL_NOTE_DETAIL_SAVE)
  MessageChannel send();
}
