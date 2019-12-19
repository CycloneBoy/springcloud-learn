package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.HouseTag;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {

    HouseTag findByNameAndHouseId(String name, Long houseId);

    List<HouseTag> findAllByHouseId(Long id);

    List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
}
