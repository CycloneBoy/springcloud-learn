package com.cycloneboy.springcloud.kafkahello.kafka.mafengwo;

import static com.cycloneboy.springcloud.common.common.Constants.KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT;

import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelImageDto;
import com.cycloneboy.springcloud.common.kafka.TravelImageStreams;
import com.cycloneboy.springcloud.common.utils.MafengwoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-08-25 23:52
 */
@Slf4j
@Component
@EnableBinding(value = {TravelImageStreams.class})
public class TravelImageListener {

  @StreamListener(KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT)
  public void receive(@Payload TravelImageDto travelImageDto) {
    log.info("receive travel image :{}", travelImageDto);
    MafengwoUtils.saveTravelImage(travelImageDto);

  }


}
