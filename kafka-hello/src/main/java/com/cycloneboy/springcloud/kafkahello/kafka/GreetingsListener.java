package com.cycloneboy.springcloud.kafkahello.kafka;

import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-08-25 16:59
 */
@Slf4j
@Component
public class GreetingsListener {


  @StreamListener(GreetingsStreams.INPUT)
  public void handleGreetings(@Payload TravelNoteDto travelNoteDto) {
    log.info("Received greetings: {}", travelNoteDto);
  }

}
