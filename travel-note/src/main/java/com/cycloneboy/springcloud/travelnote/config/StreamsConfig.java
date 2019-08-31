package com.cycloneboy.springcloud.travelnote.config;

import com.cycloneboy.springcloud.common.kafka.TravelNoteDetailStreams;
import com.cycloneboy.springcloud.common.kafka.TravelNoteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Create by  sl on 2019-08-26 00:14
 */
@Slf4j
@EnableBinding(value = {TravelNoteStreams.class, TravelNoteDetailStreams.class})
public class StreamsConfig {

}
