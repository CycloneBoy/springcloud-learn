package com.cycloneboy.springcloud.dataprocess.dao;

import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_5000;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetailInt;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

/**
 * Create by  sl on 2019-11-30 13:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelHotNoteDetailRepoTest {

  @Autowired
  private TravelHotNoteDetailRepository travelHotNoteDetailRepository;

  @Autowired
  protected TravelHotNoteDetailIntRepository travelHotNoteDetailIntRepository;

  @Test
  public void testQuery() {
    TravelHotNoteDetail hotNotes = travelHotNoteDetailRepository.findById(100L).get();
    log.info(hotNotes.toString());


  }

  @Test
  public void testQueryTotal() {
    Long total = travelHotNoteDetailRepository.getTotal();
    log.info("total size :{}", total);
  }

  @Test
  public void testQueryByPage() {

    for (int i = 0; i < 10; i++) {
      Page<TravelHotNoteDetail> list = travelHotNoteDetailRepository.findAll(PageRequest.of(i, 10));

      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

    }
  }


  /**
   * 转移数据
   * <p>
   * 完成数据转移:page: 336
   */
  @Test
  public void testTransformData() {

    Long totalNote = travelHotNoteDetailRepository.getTotal();

    List<TravelHotNoteDetailInt> travelHotNoteDetailIntList = new ArrayList<>();

    for (int i = 0; i < totalNote / PAGE_SIZE_5000 + 1; i++) {
//    for (int i = 0; i < 2; i++) {
      Page<TravelHotNoteDetail> list = travelHotNoteDetailRepository
          .findAll(PageRequest.of(i, PAGE_SIZE_5000));

//      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

      travelHotNoteDetailIntList.clear();

      list.getContent().forEach(hotNoteDetail -> {
        TravelHotNoteDetailInt travelHotNoteDetailInt = new TravelHotNoteDetailInt();
        BeanUtils.copyProperties(hotNoteDetail, travelHotNoteDetailInt);
        travelHotNoteDetailInt
            .setTravelViewCount(StringUtils.isEmpty(hotNoteDetail.getTravelViewCount()) ? 0
                : Integer.parseInt(hotNoteDetail.getTravelViewCount()));
        travelHotNoteDetailInt
            .setTravelCommentCount(StringUtils.isEmpty(hotNoteDetail.getTravelCommentCount()) ? 0
                : Integer.parseInt(hotNoteDetail.getTravelCommentCount()));
        travelHotNoteDetailInt
            .setTravelUpCount(StringUtils.isEmpty(hotNoteDetail.getTravelUpCount()) ? 0
                : Integer.parseInt(hotNoteDetail.getTravelUpCount()));

        travelHotNoteDetailIntList.add(travelHotNoteDetailInt);
      });

      travelHotNoteDetailIntRepository.saveAll(travelHotNoteDetailIntList);
    }

  }
}
