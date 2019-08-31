package com.cycloneboy.springcloud.kafkahello.kafka.mafengwo;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_NOTE_OUTPUT;

import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.common.kafka.TravelNoteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Create by  sl on 2019-08-31 21:16
 */
@Slf4j
@EnableBinding(TravelNoteStreams.class)
public class TravelNoteReceiver {

  @StreamListener(KAFKA_SENDER_TRAVEL_NOTE_OUTPUT)
  public void receive(TravelNote entity) {
    log.info("receive: {}", entity.toString());
  }

}
