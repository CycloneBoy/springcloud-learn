package com.cycloneboy.springcloud.dataprocess.dao;

import com.cycloneboy.springcloud.dataprocess.entity.TravelHotNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelHotNoteDetailRepository extends JpaRepository<TravelHotNoteDetail, Long> {

  @Query(value = "select count(*) from t_travel_hot_destination_note", nativeQuery = true)
  Long getTotal();

}
