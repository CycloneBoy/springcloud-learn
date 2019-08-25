package com.cycloneboy.springcloud.mafengwo.service;

import com.cycloneboy.springcloud.common.kafka.TravelImageStreams;
import com.cycloneboy.springcloud.mafengwo.entity.TravelImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * Create by  sl on 2019-08-25 23:38
 */
@Slf4j
@Service
public class TravelImageSenderService {

  @Autowired
  private TravelImageStreams travelImageStreams;

  public void send(TravelImage travelImage) {
    log.info("Sending TravelImage {}", travelImage);

    MessageChannel messageChannel = travelImageStreams.send();
    messageChannel.send(MessageBuilder
        .withPayload(travelImage)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());
  }

}
