package com.cycloneboy.springcloud.dataprocess.repository;

import static com.cycloneboy.springcloud.common.common.Constants.MAFENGWO_HOST_URL;
import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_100;

import com.cycloneboy.springcloud.dataprocess.dao.TravelNoteRepository;
import com.cycloneboy.springcloud.dataprocess.entity.TravelNote;
import com.cycloneboy.springcloud.dataprocess.entity.es.TravelNoteEs;
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

/**
 * Create by  sl on 2019-11-30 13:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelNoteEsRepoTest {

  @Autowired
  private TravelNoteRepository travelNoteRepository;

  @Autowired
  private TravelNoteEsRepository travelNoteEsRepository;


  @Test
  public void testQuery() {
    List<TravelNote> travelNotes = new ArrayList<>();

    travelNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }

  @Test
  public void testQueryTotal() {
    Long total = travelNoteRepository.getTotal();
    log.info("total size :{}", total);
  }

  @Test
  public void testQueryByPage() {

    Long totalNote = travelNoteRepository.getTotal();

    for (int i = 0; i < totalNote / PAGE_SIZE_100 + 1; i++) {
      Page<TravelNote> list = travelNoteRepository.findAll(PageRequest.of(i, PAGE_SIZE_100));

//      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

      List<TravelNoteEs> travelHotNoteEsList = new ArrayList<>();

      list.getContent().forEach(travelNote -> {
        TravelNoteEs travelHotNoteEs = new TravelNoteEs();
        BeanUtils.copyProperties(travelNote, travelHotNoteEs);
        travelHotNoteEs.setUrl(MAFENGWO_HOST_URL + travelNote.getUrl());
        travelHotNoteEs.setAuthorUrl(MAFENGWO_HOST_URL + travelNote.getAuthorUrl());
        travelHotNoteEsList.add(travelHotNoteEs);
      });

      travelNoteEsRepository.saveAll(travelHotNoteEsList);
    }

  }
}
