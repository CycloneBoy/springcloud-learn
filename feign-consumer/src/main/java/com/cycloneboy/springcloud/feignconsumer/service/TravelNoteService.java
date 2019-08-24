package com.cycloneboy.springcloud.feignconsumer.service;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.feignconsumer.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by  sl on 2019-08-12 23:56
 */
@FeignClient(value = "mafengwo", fallback = HelloServiceFallback.class)
public interface TravelNoteService {

    @RequestMapping("/travelnote/{id}")
    BaseResponse queryTravelNote(@PathVariable("id") Integer id);


    @GetMapping("/hello/hello")
    BaseResponse queryTravelNote();


    @GetMapping("/hello/hello1")
    BaseResponse queryTravelNote(@RequestParam("name") String  name);


    @GetMapping("/hello/hello2")
    BaseResponse queryTravelNote(@RequestHeader("name") String  name, @RequestHeader("age") Integer age);


    @PostMapping("/hello/hello3")
    BaseResponse queryTravelNote(@RequestBody TravelNoteDto travelNoteDto);

}
