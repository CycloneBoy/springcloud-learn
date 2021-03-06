package com.cycloneboy.springcloud.dataprocess.repository;

import com.cycloneboy.springcloud.dataprocess.entity.es.TravelHotNoteDetailEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Component
public interface TravelHotNoteDetailEsRepository extends
    ElasticsearchRepository<TravelHotNoteDetailEs, Long> {


}
