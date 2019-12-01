package com.cycloneboy.springcloud.dataprocess.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.dataprocess.service.TravelHotNoteDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-11-30 17:03
 */
@Slf4j
@RequestMapping("/travel/hotenode/detail")
@RestController
public class TravelHotNoteDetailController {


  @Autowired
  private TravelHotNoteDetailService hotNoteDetailService;

  /**
   * 导入mysql 数据到es
   *
   * @param start
   * @param end
   * @return
   */
  @GetMapping("/transform")
  public BaseResponse transformDataToEs(@RequestParam(name = "start") int start,
      @RequestParam(name = "end") int end) {
    Integer total = hotNoteDetailService.transformDataToEs(start, end);
    return new BaseResponse(total);
  }
}
