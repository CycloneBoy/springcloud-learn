package com.cycloneboy.springcloud.feignconsumer.fallback;

import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.mafengwoserviceapi.service.TravelNoteService;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-08-23 23:24
 */
@Component
public class HelloServiceFallback implements TravelNoteService {

  @Override
  public BaseResponse queryTravelNote(Integer id) {
    return BaseResponse.failed();
  }

  @Override
  public BaseResponse queryTravelNote() {
    return BaseResponse.failed();
  }

  @Override
  public BaseResponse queryTravelNote(String name) {
    return BaseResponse.failed();
  }

  @Override
  public BaseResponse queryTravelNote(String name, Integer age) {
    return BaseResponse.failed("error");
  }

  @Override
  public BaseResponse queryTravelNote(TravelNoteDto travelNoteDto) {
    return new BaseResponse(HttpExceptionEnum.FAILED, "error");
  }
}
