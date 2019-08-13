package com.cycloneboy.springcloud.consumerserver.controller;

import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.PageResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteRequest;
import com.cycloneboy.springcloud.consumerserver.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cycloneboy.springcloud.common.common.Constants.TRAVEL_NOTE_URI;


/**
 * Create by  sl on 2019-08-11 14:39
 */
@Slf4j
@RestController
@RequestMapping("/travelnote")
public class TravelNoteController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TravelNoteService travelNoteService;


    @GetMapping("/")
    public BaseResponse startCrawlHotNoteList(@RequestParam(name = "year") int year,
                                              @RequestParam(name = "month") int month,
                                              @RequestParam(name = "day") int day) {

        String travelNoteUrl = TRAVEL_NOTE_URI + "/?year=" + year + "&month=" + month + "&day=" + day;
        return restTemplate.getForEntity(travelNoteUrl,BaseResponse.class).getBody();
    }

    /**
     *  根据ID查询游记
     * @return BaseResponse
     */
    @GetMapping
    public BaseResponse queryTravelNoteList(@Valid TravelNoteRequest request)  {
        String url = TRAVEL_NOTE_URI + "?year={1}&month={2}&day={3}";

        PageResponse pageResponse = new PageResponse();
        pageResponse.setData(new ArrayList<TravelNoteDto>());

        return  restTemplate.getForObject(url,pageResponse.getClass(),request.getYear(),request.getMonth(),request.getDay());

    }

    /**
     *  根据ID查询游记
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/{id}")
    public BaseResponse queryTravelNote(@PathVariable Integer id) {
        return travelNoteService.queryTravelNote(id);
    }

    /**
     * 保存游记
     * @param travelNote
     * @return BaseResponse
     */
    @PostMapping
    public BaseResponse addTravelNote(@Valid @RequestBody TravelNoteDto travelNote) {
        String url = TRAVEL_NOTE_URI ;
        TravelNoteDto travelNoteDto = restTemplate.postForObject(url, travelNote, TravelNoteDto.class);
        return  new BaseResponse(travelNoteDto);
    }

    /**
     *  删除游记
     * @param id
     * @return BaseResponse
     */
    @DeleteMapping("/{id}")
    public BaseResponse deleteTravelNoteById(@PathVariable Integer id) {
        String url = TRAVEL_NOTE_URI + "/{1}";
        restTemplate.delete(url,id);

        return new BaseResponse();
    }

    /**
     * 修改游记
     * @param travelNote
     * @return
     */
    @PutMapping
    public BaseResponse update(@Valid @RequestBody TravelNoteDto travelNote) {
        String url = TRAVEL_NOTE_URI ;
        restTemplate.put(url, travelNote);
        return  new BaseResponse();
    }
}
