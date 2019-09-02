package com.cycloneboy.springcloud.kafkahello.kafka.mafengwo;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_AUTHOR_OUTPUT;

import com.cycloneboy.springcloud.common.entity.NoteAuthor;
import com.cycloneboy.springcloud.common.kafka.TravelAuthorStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Create by  sl on 2019-08-31 21:16
 */
@Slf4j
@EnableBinding(TravelAuthorStreams.class)
public class TravelAuthorReceiver {

  @StreamListener(KAFKA_SENDER_TRAVEL_AUTHOR_OUTPUT)
  public void receive(NoteAuthor entity) {
    log.info("receive: {}", entity.toString());
  }

}
