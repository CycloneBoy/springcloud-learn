package com.cycloneboy.springcloud.consumerserver.service;

import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import static com.cycloneboy.springcloud.common.common.Constants.TRAVEL_NOTE_URI;


/**
 * Create by  sl on 2019-08-11 17:51
 */
@Slf4j
@Service
public class TravelNoteService {

    @Autowired
    private RestTemplate restTemplate;


    /**
     *  根据ID查询游记
     * @param id
     * @return BaseResponse
     */
    @HystrixCommand(fallbackMethod = "queryTravelNoteFallback")
    public BaseResponse queryTravelNote( Integer id) {
        String url = TRAVEL_NOTE_URI + "/{1}";
        return  restTemplate.getForObject(url,BaseResponse.class,id);

    }

    public BaseResponse queryTravelNoteFallback(Integer id){
        return new  BaseResponse(HttpExceptionEnum.REMOTE_API_FAILED);
    }
}
