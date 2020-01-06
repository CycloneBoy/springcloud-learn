package com.cycloneboy.springcloud.springlearn.springdb.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelNote;
import com.cycloneboy.springcloud.springlearn.springdb.repository.TravelNoteRepository;
import com.cycloneboy.springcloud.springlearn.springdb.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-06 11:04
 */
@Slf4j
@Service
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

  @Override
  public void deleteById(Integer id) {
    TravelNote note = getById(id);

    log.info("查询note: {} - {}", id, JSONUtils.toJSONString(note));

    travelNoteRepository.deleteById(id);
    log.info("执行完删除ID:{}", id);
    log.info("进行暴露异常:{} -{}", id, 10 / (id % 10));

    log.info("返回");
  }
}
