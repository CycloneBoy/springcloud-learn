package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by  sl on 2019-11-30 13:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelHotNoteDetailRepoTest {

  @Autowired
  private TravelHotNoteDetailRepository travelHotNoteDetailRepository;


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
}
