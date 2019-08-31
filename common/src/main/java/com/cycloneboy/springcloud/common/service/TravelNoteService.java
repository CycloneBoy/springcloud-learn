package com.cycloneboy.springcloud.common.service;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
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

@RequestMapping("/refactor")
public interface TravelNoteService {

  @RequestMapping("/travelnote/{id}")
  BaseResponse queryTravelNote(@PathVariable("id") Integer id);


  @GetMapping("/hello/hello4")
  BaseResponse queryTravelNote();


  @GetMapping("/hello/hello5")
  BaseResponse queryTravelNote(@RequestParam("name") String name);


  @GetMapping("/hello/hello6")
  BaseResponse queryTravelNote(@RequestHeader("name") String name,
      @RequestHeader("age") Integer age);


  @PostMapping("/hello/hello7")
  BaseResponse queryTravelNote(@RequestBody TravelNoteDto travelNoteDto);

}
