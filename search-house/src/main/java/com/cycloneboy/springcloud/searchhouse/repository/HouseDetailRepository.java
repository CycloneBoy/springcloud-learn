package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.HouseDetail;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long> {

    HouseDetail findByHouseId(Long houseId);

    List<HouseDetail> findAllByHouseIdIn(List<Long> houseIds);
}
