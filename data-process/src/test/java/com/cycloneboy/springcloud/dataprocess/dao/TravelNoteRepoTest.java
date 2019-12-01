package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelNote;
import java.util.List;
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
public class TravelNoteRepoTest {

  @Autowired
  private TravelNoteRepository travelNoteRepository;


  @Test
  public void testQuery() {
    List<TravelNote> travelNotes = travelNoteRepository.findAll();

    travelNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }

  @Test
  public void testQueryTotal() {
    Long total = travelNoteRepository.getTotal();
    log.info("total size :{}", total);
  }

  @Test
  public void testQueryByPage() {

    for (int i = 0; i < 10; i++) {
      Page<TravelNote> list = travelNoteRepository.findAll(PageRequest.of(i, 10));

      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

    }

  }
}
