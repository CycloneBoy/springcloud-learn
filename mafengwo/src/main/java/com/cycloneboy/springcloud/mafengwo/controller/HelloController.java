package com.cycloneboy.springcloud.mafengwo.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Create by  sl on 2019-08-13 22:50
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/hello")
    public BaseResponse queryTravelNote(){
        return new BaseResponse();
    }


    @GetMapping("/hello1")
    public BaseResponse queryTravelNote(@RequestParam String  name){
        return new BaseResponse(name);
    }


    @GetMapping("/hello2")
    public BaseResponse queryTravelNote(@RequestHeader String  name,@RequestHeader Integer age){
        return new BaseResponse(name + " " + age);
    }


    @PostMapping("/hello3")
    public BaseResponse queryTravelNote(@RequestBody TravelNoteDto travelNoteDto){
        return new BaseResponse(travelNoteDto);
    }
}
