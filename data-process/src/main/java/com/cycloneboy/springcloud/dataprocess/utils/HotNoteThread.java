package com.cycloneboy.springcloud.dataprocess.utils;

import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_1000;

import com.cycloneboy.springcloud.dataprocess.dao.TravelHotNoteDetailRepository;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import com.cycloneboy.springcloud.dataprocess.entity.es.TravelHotNoteDetailEs;
import com.cycloneboy.springcloud.dataprocess.repository.TravelHotNoteDetailEsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

/**
 * Create by  sl on 2019-11-30 17:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class HotNoteThread implements Runnable {

  @Autowired
  private TravelHotNoteDetailRepository hotNoteDetailRepository;

  @Autowired
  private TravelHotNoteDetailEsRepository hotNoteDetailEsRepository;

  CountDownLatch cdl;

//  List<TravelHotNoteDetail> travelHotNoteList;

  Integer pageSize;


  @Override
  public void run() {
//    TravelHotNoteDetailRepository hotNoteDetailRepository = (TravelHotNoteDetailRepository) SpringUtils
//        .getBean("travelHotNoteDetailRepository");
//
//    TravelHotNoteDetailEsRepository hotNoteDetailEsRepository = (TravelHotNoteDetailEsRepository) SpringUtils
//        .getBean("travelHotNoteDetailEsRepository");

    List<TravelHotNoteDetailEs> travelHotNoteEsList = new ArrayList<>();

    Page<TravelHotNoteDetail> list = hotNoteDetailRepository
        .findAll(PageRequest.of(pageSize, PAGE_SIZE_1000));

    list.getContent().forEach(hotNoteDetail -> {
      TravelHotNoteDetailEs travelHotNoteEs = new TravelHotNoteDetailEs();

      BeanUtils.copyProperties(hotNoteDetail, travelHotNoteEs);
      travelHotNoteEs
          .setTravelViewCount(StringUtils.isEmpty(hotNoteDetail.getTravelViewCount()) ? 0L
              : Long.parseLong(hotNoteDetail.getTravelViewCount()));
      travelHotNoteEs
          .setTravelCommentCount(StringUtils.isEmpty(hotNoteDetail.getTravelCommentCount()) ? 0L
              : Long.parseLong(hotNoteDetail.getTravelCommentCount()));
      travelHotNoteEs
          .setTravelUpCount(StringUtils.isEmpty(hotNoteDetail.getTravelUpCount()) ? 0L
              : Long.parseLong(hotNoteDetail.getTravelUpCount()));

      travelHotNoteEsList.add(travelHotNoteEs);
    });

    hotNoteDetailEsRepository.saveAll(travelHotNoteEsList);
    log.info("插入第 {} 页数据成功，当前线程是: {}", pageSize, Thread.currentThread().getName());
    cdl.countDown();
  }
}
