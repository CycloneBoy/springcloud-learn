package com.cycloneboy.springcloud.kafkahello.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Create by  sl on 2019-08-25 16:50
 */
@Slf4j
@EnableBinding(value = {GreetingsStreams.class})
public class StreamsConfig {

}
