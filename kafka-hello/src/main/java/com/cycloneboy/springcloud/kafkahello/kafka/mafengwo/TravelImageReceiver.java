package com.cycloneboy.springcloud.kafkahello.kafka.mafengwo;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT;

import com.cycloneboy.springcloud.common.entity.TravelImage;
import com.cycloneboy.springcloud.common.kafka.TravelImageStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Create by  sl on 2019-08-31 21:16
 */
@Slf4j
@EnableBinding(TravelImageStreams.class)
public class TravelImageReceiver {

  @StreamListener(KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT)
  public void receive(TravelImage travelImage) {
    log.info("receive: {}", travelImage.toString());
  }

}
