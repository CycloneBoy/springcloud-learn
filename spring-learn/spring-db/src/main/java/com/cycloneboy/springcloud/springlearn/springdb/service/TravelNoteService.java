package com.cycloneboy.springcloud.springlearn.springdb.service;

import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelNote;

/**
 * Create by  sl on 2020-01-06 10:58
 */
public interface TravelNoteService {

  TravelNote getByDay(Integer year, Integer month, Integer day);

  TravelNote save(TravelNote travelNote);

  TravelNote getById(Integer id);

  TravelNote update(TravelNote note);

  void deleteById(Integer id);
}
