package com.cycloneboy.springcloud.elasticsearch.repository;


import com.cycloneboy.springcloud.elasticsearch.entity.TravelHotNoteDetailEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Component
public interface TravelHotNoteDetailEsRepository extends
    ElasticsearchRepository<TravelHotNoteDetailEs, Long> {


}
