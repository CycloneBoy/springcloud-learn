package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.HousePicture;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface HousePictureRepository extends CrudRepository<HousePicture, Long> {

    List<HousePicture> findAllByHouseId(Long id);
}
