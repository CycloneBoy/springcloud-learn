package com.cycloneboy.springcloud.kafkahello.service;

import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.kafkahello.kafka.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * Create by  sl on 2019-08-25 16:54
 */
@Slf4j
@Service
public class GreetingsService {

  @Autowired
  private GreetingsStreams greetingsStreams;


  public void sendGreeting(TravelNoteDto travelNoteDto) {
    log.info("Sending greetings {}", travelNoteDto);

    MessageChannel messageChannel = greetingsStreams.outboundGreetings();
    messageChannel.send(MessageBuilder
        .withPayload(travelNoteDto)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());
  }
}
