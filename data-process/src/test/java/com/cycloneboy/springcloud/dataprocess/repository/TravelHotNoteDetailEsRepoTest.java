package com.cycloneboy.springcloud.dataprocess.repository;

import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_1000;

import com.cycloneboy.springcloud.dataprocess.dao.TravelHotNoteDetailRepository;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNote;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetailEs;
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
public class TravelHotNoteDetailEsRepoTest {

  @Autowired
  private TravelHotNoteDetailRepository hotNoteDetailRepository;

  @Autowired
  private TravelHotNoteDetailEsRepository hotNoteDetailEsRepository;


  @Test
  public void testQuery() {
    List<TravelHotNote> hotNotes = new ArrayList<>();

    hotNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }

  @Test
  public void testQueryTotal() {
    Long total = hotNoteDetailRepository.getTotal();
    log.info("total size :{}", total);
  }

  /**
   * 总共总数: 3660522
   * <p>
   * 第一次转存278页
   */
  @Test
  public void testQueryByPage() {

    Long totalNote = hotNoteDetailRepository.getTotal();

    List<TravelHotNoteDetailEs> travelHotNoteEsList = new ArrayList<>(PAGE_SIZE_1000);

    for (int i = 0; i < totalNote / PAGE_SIZE_1000 + 1; i++) {
//    for (int i = 0; i < 2; i++) {
      Page<TravelHotNoteDetail> list = hotNoteDetailRepository
          .findAll(PageRequest.of(i, PAGE_SIZE_1000));

//      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

      travelHotNoteEsList.clear();

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
    }

  }
}
