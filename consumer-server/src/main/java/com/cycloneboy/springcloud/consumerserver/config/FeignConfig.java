package com.cycloneboy.springcloud.consumerserver.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @program: springclouddemo
 * @description:
 * @author: cycloneboy
 * @create:2018-09-16 17:45
 **/
//@Configuration
//public class FeignConfig {
//
//    @Bean
//    public Retryer feignRetryer(){
//        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1),5);
//    }
//
//}
