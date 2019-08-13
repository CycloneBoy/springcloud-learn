package com.cycloneboy.springcloud.mafengwo.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.mafengwoserviceapi.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-13 23:57
 */

@Slf4j
@RestController
public class RefactorTravelNoteController implements TravelNoteService {

    @Autowired
    private com.cycloneboy.springcloud.mafengwo.service.TravelNoteService travelNoteService;


    @Override
    public BaseResponse queryTravelNote(Integer id) {
        return new BaseResponse(travelNoteService.getById(id));
    }

    @Override
    public BaseResponse queryTravelNote() {
        return new BaseResponse();
    }

    @Override
    public BaseResponse queryTravelNote(String name) {
        return new BaseResponse(name);
    }

    @Override
    public BaseResponse queryTravelNote(String name, Integer age) {
        return new BaseResponse(name + " " + age);
    }

    @Override
    public BaseResponse queryTravelNote(TravelNoteDto travelNoteDto) {
        return new BaseResponse(travelNoteDto);
    }
}
