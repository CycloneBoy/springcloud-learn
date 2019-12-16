package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteInt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelHotNoteIntRepository extends JpaRepository<TravelHotNoteInt, Long> {

  @Query(value = "select count(*) from t_hot_travel_note_list_1", nativeQuery = true)
  Long getTotal();

}
