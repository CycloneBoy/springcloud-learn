package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.Subway;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface SubwayRepository extends CrudRepository<Subway, Long> {

    List<Subway> findAllByCityEnName(String cityEnName);
}
