package com.cycloneboy.springcloud.travelnote.kafka;

import com.cycloneboy.springcloud.common.entity.NoteAuthor;
import com.cycloneboy.springcloud.common.kafka.TravelAuthorStreams;
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
public class TravelAuthorSenderService {

  @Autowired
  private TravelAuthorStreams travelAuthorStreams;

  public void send(NoteAuthor noteAuthor) {
    log.info("Sending NoteAuthor {}", noteAuthor);

    MessageChannel messageChannel = travelAuthorStreams.send();
    messageChannel.send(MessageBuilder.withPayload(noteAuthor).build());
  }

}
