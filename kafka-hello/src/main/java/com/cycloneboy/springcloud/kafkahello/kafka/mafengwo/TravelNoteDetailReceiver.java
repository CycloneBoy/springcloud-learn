package com.cycloneboy.springcloud.kafkahello.kafka.mafengwo;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_NOTE_DETAIL_OUTPUT;

import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.common.kafka.TravelNoteDetailStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Create by  sl on 2019-08-31 21:16
 */
@Slf4j
@EnableBinding(TravelNoteDetailStreams.class)
public class TravelNoteDetailReceiver {

  @StreamListener(KAFKA_SENDER_TRAVEL_NOTE_DETAIL_OUTPUT)
  public void receive(TravelNoteDetail entity) {
    log.info("receive: {}", entity.toString());
  }

}
