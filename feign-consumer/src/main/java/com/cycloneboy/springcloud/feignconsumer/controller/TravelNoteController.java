package com.cycloneboy.springcloud.feignconsumer.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.feignconsumer.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-11 14:39
 */
@Slf4j
@RestController
@RequestMapping("/travelnote")
public class TravelNoteController {

    @Autowired
    private TravelNoteService travelNoteService;

//    @Autowired
//    private RefactorHelloService refactorHelloService;


    /**
     *  根据ID查询游记
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/{id}")
    public BaseResponse queryTravelNote(@PathVariable Integer id) {
        return travelNoteService.queryTravelNote(id);
    }


    @GetMapping("/feign-consumer2")
    public BaseResponse queryTravelNote() {
        StringBuilder sb = new StringBuilder();
        sb.append(travelNoteService.queryTravelNote()).append("\n");
        sb.append(travelNoteService.queryTravelNote("hello")).append("\n");
        sb.append(travelNoteService.queryTravelNote("world", 30)).append("\n");
        TravelNoteDto travelNoteDto = TravelNoteDto.builder().authorName("hello")
            .noteImageUrl("http").build();
        sb.append(travelNoteService.queryTravelNote(travelNoteDto)).append("\n");

        return new BaseResponse(sb.toString());
    }

//    @GetMapping("/feign-consumer3")
//    public BaseResponse queryTravelNote3() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(refactorHelloService.queryTravelNote()).append("\n");
//        sb.append(refactorHelloService.queryTravelNote("hello3")).append("\n");
//        sb.append(refactorHelloService.queryTravelNote("world3", 30)).append("\n");
//        TravelNoteDto travelNoteDto =
//            TravelNoteDto.builder().authorName("hello3").noteImageUrl("http3").build();
//        sb.append(refactorHelloService.queryTravelNote(travelNoteDto)).append("\n");
//
//        return new BaseResponse(sb.toString());
//    }
}
