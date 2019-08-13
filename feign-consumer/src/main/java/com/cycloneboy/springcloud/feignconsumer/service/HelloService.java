package com.cycloneboy.springcloud.feignconsumer.service;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by  sl on 2019-08-13 22:57
 */
//@FeignClient("mafengwo")
public interface HelloService {

//    @GetMapping("/hello")
//    public BaseResponse queryTravelNote();
//
//
//    @GetMapping("/hello1")
//    public BaseResponse queryTravelNote(@RequestParam("name") String  name);
//
//
//    @GetMapping("/hello2")
//    public BaseResponse queryTravelNote(@RequestHeader("name") String  name, @RequestHeader("age") Integer age);
//
//
//    @GetMapping("/hello3")
//    public BaseResponse queryTravelNote(@RequestBody TravelNoteDto travelNoteDto);
}
