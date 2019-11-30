package com.cycloneboy.springcloud.dataprocess.repository;

import static com.cycloneboy.springcloud.common.common.Constants.PAGE_SIZE_10;

import com.cycloneboy.springcloud.dataprocess.dao.TravelHotNoteRepository;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNote;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteEs;
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
public class TravelHotNoteEsRepoTest {

  @Autowired
  private TravelHotNoteRepository travelHotNoteRepository;

  @Autowired
  private TravelHotNoteEsRepository travelHotNoteEsRepository;


  @Test
  public void testQuery() {
    List<TravelHotNote> hotNotes = new ArrayList<>();

    hotNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }

  @Test
  public void testQueryTotal() {
    Long total = travelHotNoteRepository.getTotal();
    log.info("total size :{}", total);
  }

  @Test
  public void testQueryByPage() {

    Long totalNote = travelHotNoteRepository.getTotal();

    for (int i = 0; i < totalNote / PAGE_SIZE_10 + 1; i++) {
      Page<TravelHotNote> list = travelHotNoteRepository.findAll(PageRequest.of(i, PAGE_SIZE_10));

//      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);

      List<TravelHotNoteEs> travelHotNoteEsList = new ArrayList<>(PAGE_SIZE_10);

      list.getContent().forEach(travelHotNote -> {
        TravelHotNoteEs travelHotNoteEs = new TravelHotNoteEs();
        BeanUtils.copyProperties(travelHotNote, travelHotNoteEs);
        travelHotNoteEs.setTotalNumber(StringUtils.isEmpty(travelHotNote.getTotalNumber()) ? 0L
            : Long.parseLong(travelHotNote.getTotalNumber()));
        travelHotNoteEs.setTotalPage(StringUtils.isEmpty(travelHotNote.getTotalPage()) ? 0L
            : Long.parseLong(travelHotNote.getTotalPage()));
        travelHotNoteEs
            .setImageTotalNumber(StringUtils.isEmpty(travelHotNote.getImageTotalNumber()) ? 0L
                : Long.parseLong(travelHotNote.getImageTotalNumber()));

        travelHotNoteEsList.add(travelHotNoteEs);
      });

      travelHotNoteEsRepository.saveAll(travelHotNoteEsList);
    }

  }
}
