package com.cycloneboy.springcloud.feignconsumer.service;

import com.cycloneboy.springcloud.mafengwoserviceapi.service.TravelNoteService;

/**
 * Create by  sl on 2019-08-23 22:54
 */
//@FeignClient(value = "mafengwo", fallback = HelloServiceFallback.class)
public interface RefactorHelloService extends TravelNoteService {

}
