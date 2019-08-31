package com.cycloneboy.springcloud.travelnote.kafka;

import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.common.kafka.TravelNoteStreams;
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
public class TravelNoteSenderService {

  @Autowired
  private TravelNoteStreams travelNoteStreams;

  public void send(TravelNote travelNote) {
    log.info("Sending TravelNote {}", travelNote);

    MessageChannel messageChannel = travelNoteStreams.send();
    messageChannel.send(MessageBuilder.withPayload(travelNote).build());
  }

}
