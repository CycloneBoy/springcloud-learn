package com.cycloneboy.springcloud.springlearn.springdb.service.impl;

import com.alibaba.fastjson.JSON;
import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelNote;
import com.cycloneboy.springcloud.springlearn.springdb.repository.TravelNoteRepository;
import com.cycloneboy.springcloud.springlearn.springdb.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by  sl on 2020-01-06 11:04
 */
@Slf4j
@Service
@Transactional
public class TravelNoteServiceImpl implements TravelNoteService {

  @Autowired
  private TravelNoteRepository travelNoteRepository;

  @Override
  public TravelNote getByDay(Integer year, Integer month, Integer day) {
    TravelNote travelNote = travelNoteRepository
        .findTopByYearAndMonthAndDay(year, month, day);
    return travelNote;
  }

  @Override
  public TravelNote save(TravelNote travelNote) {

    TravelNote note = travelNoteRepository.save(travelNote);

    log.info("{}", note.toString());

    return note;
  }

  @Override
  public TravelNote getById(Integer id) {
    return travelNoteRepository.findById(id).orElse(new TravelNote());
  }

  @Override
  public TravelNote update(TravelNote note) {
    return travelNoteRepository.save(note);
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED, timeout = 30,
      isolation = Isolation.DEFAULT, rollbackFor = {RuntimeException.class, Exception.class})
  @Override
  public void deleteById(Integer id) {
    TravelNote note = getById(id);

    log.info("查询note: {} - {}", id, JSON.toJSONString(note));
    if (travelNoteRepository.existsById(id)) {
      travelNoteRepository.deleteById(id);
    }

    log.info("执行完删除ID:{}", id);
    log.info("进行暴露异常:{} - {}", id, 10 / (id % 10));

    log.info("返回");
  }
}
