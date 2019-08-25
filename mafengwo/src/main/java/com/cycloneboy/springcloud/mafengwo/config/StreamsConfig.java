package com.cycloneboy.springcloud.mafengwo.config;

import com.cycloneboy.springcloud.common.kafka.TravelImageStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Create by  sl on 2019-08-26 00:14
 */
@Slf4j
@EnableBinding(value = {TravelImageStreams.class})
public class StreamsConfig {

}
