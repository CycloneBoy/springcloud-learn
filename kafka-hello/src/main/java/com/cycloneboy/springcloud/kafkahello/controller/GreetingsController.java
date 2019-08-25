package com.cycloneboy.springcloud.kafkahello.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteDto;
import com.cycloneboy.springcloud.kafkahello.service.GreetingsService;
import com.cycloneboy.springcloud.kafkahello.service.SenderService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-25 16:56
 */
@Slf4j
@RestController
public class GreetingsController {

  @Autowired
  private GreetingsService greetingsService;

  @Autowired
  private SenderService senderService;


  @GetMapping("/greetings")
  public void greetings(@RequestParam("destination") String destination) {
    TravelNoteDto travelNoteDto = TravelNoteDto.builder()
        .authorName("cycloneboy")
        .destination(destination)
        .noteImageUrl("test").build();
    greetingsService.sendGreeting(travelNoteDto);
  }

  @GetMapping("/test")
  public BaseResponse test(@RequestParam("destination") String destination) {
    TravelNoteDto travelNoteDto = TravelNoteDto.builder()
        .authorName("cycloneboy")
        .noteImageUrl("test")
        .authorUrl("test")
        .destination(destination)
        .createTime(new Date())
        .noteImageUrl("test").build();

    senderService.sendHello(travelNoteDto);
    return new BaseResponse(travelNoteDto);
  }
}
