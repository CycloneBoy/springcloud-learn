package com.cycloneboy.springcloud.goodskill.repository;

import com.cycloneboy.springcloud.goodskill.entity.Seckill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-12-10 17:34
 */
@Repository
public interface SeckillRepository extends JpaRepository<Seckill, Long> {


}
