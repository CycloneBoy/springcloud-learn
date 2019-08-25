package com.cycloneboy.springcloud.kafkahello.service;

import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.kafkahello.kafka.SinkSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * Create by  sl on 2019-08-25 18:34
 */
@Slf4j
@Service
public class SenderService {

  @Autowired
  private SinkSender sinkSender;

  public void sendHello(TravelNoteDto travelNoteDto) {
    log.info("Sending greetings {}", travelNoteDto);

    MessageChannel messageChannel = sinkSender.output();
    messageChannel.send(MessageBuilder
        .withPayload(travelNoteDto)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());
  }

}
