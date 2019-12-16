package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNote;
import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteInt;
import com.cycloneboy.springcloud.dataprocess.repository.TravelHotNoteEsRepository;
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
public class TravelHotNoteRepoTest {

  @Autowired
  private TravelHotNoteRepository travelHotNoteRepository;

  @Autowired
  private TravelHotNoteEsRepository travelHotNoteEsRepository;

  @Autowired
  private TravelHotNoteIntRepository travelHotNoteIntRepository;


  @Test
  public void testQuery() {
    List<TravelHotNote> hotNotes = travelHotNoteRepository.findAll();

    hotNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }

  @Test
  public void testQueryTotal() {
    Long total = travelHotNoteRepository.getTotal();
    log.info("total size :{}", total);
  }

  @Test
  public void testQueryByPage() {

    for (int i = 0; i < 10; i++) {
      Page<TravelHotNote> list = travelHotNoteRepository.findAll(PageRequest.of(i, 10));

      list.forEach(travelHotNote -> log.info(travelHotNote.toString()));
      log.info("---------------------------page: {}--------------------------------", i);
//      travelHotNoteEsRepository.saveAll(list);

    }

  }


  /**
   * 转移数据
   */
  @Test
  public void testTransfomData() {
    List<TravelHotNote> hotNotes = travelHotNoteRepository.findAll();

    List<TravelHotNoteInt> travelHotNoteIntList = new ArrayList<>();
    hotNotes.forEach(travelHotNote -> {

      TravelHotNoteInt travelHotNoteInt = new TravelHotNoteInt();
      BeanUtils.copyProperties(travelHotNote, travelHotNoteInt);
      travelHotNoteInt
          .setImageTotalNumber(StringUtils.isEmpty(travelHotNote.getImageTotalNumber()) ? 0
              : Integer.parseInt(travelHotNote.getImageTotalNumber()));

      travelHotNoteInt
          .setTotalPage(StringUtils.isEmpty(travelHotNote.getTotalPage()) ? 0
              : Integer.parseInt(travelHotNote.getTotalPage()));

      travelHotNoteInt
          .setTotalNumber(StringUtils.isEmpty(travelHotNote.getTotalNumber()) ? 0
              : Integer.parseInt(travelHotNote.getTotalNumber()));

      travelHotNoteIntList.add(travelHotNoteInt);
    });

    travelHotNoteIntRepository.saveAll(travelHotNoteIntList);
    log.info("转存完成:{}", travelHotNoteIntList.size());
  }
}
