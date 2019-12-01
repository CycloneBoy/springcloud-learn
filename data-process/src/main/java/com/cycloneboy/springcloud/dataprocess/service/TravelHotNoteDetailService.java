package com.cycloneboy.springcloud.dataprocess.service;

import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_1000;

import com.cycloneboy.springcloud.dataprocess.dao.TravelHotNoteDetailRepository;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import com.cycloneboy.springcloud.dataprocess.entity.es.TravelHotNoteDetailEs;
import com.cycloneboy.springcloud.dataprocess.repository.TravelHotNoteDetailEsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Create by  sl on 2019-11-30 17:06
 */
@Slf4j
@Service
public class TravelHotNoteDetailService {

  @Autowired
  private TravelHotNoteDetailRepository hotNoteDetailRepository;

  @Autowired
  private TravelHotNoteDetailEsRepository hotNoteDetailEsRepository;

  private CountDownLatch threadsSignal;

  //定义线程池数量为8,每个线程处理1000条数据
  private static ExecutorService pool = Executors
      .newFixedThreadPool(Runtime.getRuntime().availableProcessors());


  /**
   * 总共总数: 366 0522
   * <p>     3660 页
   * <p>
   * 第一次转存278页
   * <p>
   * 266,900  266 278,900
   * 500
   * 500,900
   *
   * 991  992,900
   *
   *  1230
   *
   *  1390
   */
  public Integer transformDataToEs(Integer startPage, Integer endPage) {

    List<TravelHotNoteDetailEs> travelHotNoteEsList = new ArrayList<>();

    for (int i = startPage; i < endPage; i++) {
      Page<TravelHotNoteDetail> list = hotNoteDetailRepository
          .findAll(PageRequest.of(i, PAGE_SIZE_1000));

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
      log.info("success save page : {} ", i);
    }
    Integer total = (endPage - startPage) * PAGE_SIZE_1000;

    log.info("success save page : {} -> {}, total save:{}", startPage, endPage, total);
    return total;
  }


}
