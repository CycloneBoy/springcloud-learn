package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.SubwayStation;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long> {

    List<SubwayStation> findAllBySubwayId(Long subwayId);
}
