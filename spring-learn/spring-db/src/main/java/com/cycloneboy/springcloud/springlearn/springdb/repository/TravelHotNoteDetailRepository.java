package com.cycloneboy.springcloud.springlearn.springdb.repository;

import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelHotNoteDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelHotNoteDetailRepository extends CrudRepository<TravelHotNoteDetail, Long> {

  @Query(value = "select count(*) from t_travel_hot_destination_note", nativeQuery = true)
  Long getTotal();

}
