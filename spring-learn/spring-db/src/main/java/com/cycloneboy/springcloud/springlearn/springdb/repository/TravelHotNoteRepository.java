package com.cycloneboy.springcloud.springlearn.springdb.repository;


import com.cycloneboy.springcloud.springlearn.springdb.entity.TravelHotNote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelHotNoteRepository extends CrudRepository<TravelHotNote, Long> {

  @Query(value = "select count(*) from t_hot_travel_note_list", nativeQuery = true)
  Long getTotal();

}
