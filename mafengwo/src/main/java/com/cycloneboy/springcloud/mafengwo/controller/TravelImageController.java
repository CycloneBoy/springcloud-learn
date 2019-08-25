package com.cycloneboy.springcloud.mafengwo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.PageResponse;
import com.cycloneboy.springcloud.mafengwo.entity.TravelImage;
import com.cycloneboy.springcloud.mafengwo.service.TravelImageSenderService;
import com.cycloneboy.springcloud.mafengwo.service.TravelImageService;
import com.cycloneboy.springcloud.mafengwo.utils.CommonUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-25 22:55
 */
@Slf4j
@RestController
@RequestMapping("/travelimage")
public class TravelImageController {

  @Autowired
  private TravelImageService travelImageService;

  @Autowired
  private TravelImageSenderService travelImageSenderService;

  @GetMapping("/")
  public BaseResponse getImage(@RequestParam(name = "noteid") String noteid) {

    QueryWrapper<TravelImage> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(TravelImage::getNoteId, noteid)
        .orderByDesc(TravelImage::getVoteNum);

    List<TravelImage> travelImages = travelImageService.list(queryWrapper);

    return new PageResponse(travelImages);
  }

  @GetMapping("/save")
  public BaseResponse saveImage(@RequestParam(name = "noteid") String noteid,
      @RequestParam(name = "votenum") int votenum) {

    QueryWrapper<TravelImage> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(TravelImage::getNoteId, noteid)
        .ge(TravelImage::getVoteNum, votenum)
        .orderByDesc(TravelImage::getVoteNum);

    List<TravelImage> travelImages = travelImageService.list(queryWrapper);

    CommonUtils.saveTravelImage(travelImages);
    return new PageResponse(travelImages);
  }

  @GetMapping("/kafka")
  public BaseResponse sendmageToKafka(@RequestParam(name = "noteid") String noteid,
      @RequestParam(name = "votenum") int votenum) {

    QueryWrapper<TravelImage> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(TravelImage::getNoteId, noteid)
        .ge(TravelImage::getVoteNum, votenum)
        .orderByDesc(TravelImage::getVoteNum);

    List<TravelImage> travelImages = travelImageService.list(queryWrapper);

    travelImages.forEach(travelImage -> {
      travelImageSenderService.send(travelImage);
    });

    log.info("send {} travel image to kafka", travelImages.size());

    return new PageResponse(travelImages);
  }


}
