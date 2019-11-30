package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelHotNoteRepository extends JpaRepository<TravelHotNote, Long> {

  @Query(value = "select count(*) from t_hot_travel_note_list", nativeQuery = true)
  Long getTotal();

}
