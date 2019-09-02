package com.cycloneboy.springcloud.travelnote.kafka;

import com.cycloneboy.springcloud.common.entity.TravelImage;
import com.cycloneboy.springcloud.common.kafka.TravelImageStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

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
    messageChannel.send(MessageBuilder.withPayload(travelImage).build());
  }

}
