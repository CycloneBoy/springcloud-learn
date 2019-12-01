package com.cycloneboy.springcloud.dataprocess.repository;

import com.cycloneboy.springcloud.dataprocess.entity.es.TravelNoteEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-11-30 13:41
 */
@Repository
public interface TravelNoteEsRepository extends ElasticsearchRepository<TravelNoteEs, Integer> {


}
