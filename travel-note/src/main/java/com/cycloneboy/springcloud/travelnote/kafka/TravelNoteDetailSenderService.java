package com.cycloneboy.springcloud.travelnote.kafka;

import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.common.kafka.TravelNoteDetailStreams;
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
public class TravelNoteDetailSenderService {

  @Autowired
  private TravelNoteDetailStreams travelNoteDetailStreams;

  public void send(TravelNoteDetail travelNoteDetail) {
    log.info("Sending TravelNoteDetail {}", travelNoteDetail);

    MessageChannel messageChannel = travelNoteDetailStreams.send();
    messageChannel.send(MessageBuilder.withPayload(travelNoteDetail).build());
  }

}
