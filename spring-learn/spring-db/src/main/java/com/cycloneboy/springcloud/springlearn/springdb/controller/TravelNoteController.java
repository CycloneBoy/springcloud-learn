package com.cycloneboy.springcloud.springlearn.springdb.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelNote;
import com.cycloneboy.springcloud.springlearn.springdb.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-11-30 17:03
 */
@Slf4j
@RequestMapping("/travelnote")
@RestController
public class TravelNoteController {


  @Autowired
  private TravelNoteService travelNoteService;

  @GetMapping("/getid")
  public BaseResponse getTravelNoteById(@RequestParam(name = "id") Integer id) {
    TravelNote travelNote = travelNoteService.getById(id);
    return new BaseResponse(travelNote);
  }

  /**
   * 查询每一天的蜂首游记
   *
   * @param year
   * @param month
   * @param day
   * @return
   */
  @GetMapping("/get")
  public BaseResponse getTravelNote(@RequestParam(name = "year") int year,
      @RequestParam(name = "month") int month,
      @RequestParam(name = "day") int day) {
    TravelNote travelNote = travelNoteService.getByDay(year, month, day);
    return new BaseResponse(travelNote);
  }

  @PostMapping("/save")
  public BaseResponse saveTravelNote(@RequestBody TravelNote travelNote) {
    TravelNote note = travelNoteService.save(travelNote);
    return new BaseResponse(note);
  }

  @PutMapping("/update")
  public BaseResponse updateTravelNote(@RequestBody TravelNote travelNote) {
    TravelNote note = travelNoteService.update(travelNote);
    return new BaseResponse(note);
  }

  @DeleteMapping("/delete")
  public BaseResponse deleteTravelNoteById(@RequestParam(name = "id") Integer id) {
    travelNoteService.deleteById(id);
    return new BaseResponse();
  }
}
