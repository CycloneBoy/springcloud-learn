package com.cycloneboy.springcloud.mafengwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.cycloneboy.springcloud.mafengwo.dao.*")
@EnableDiscoveryClient
@SpringBootApplication
public class MafengwoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MafengwoApplication.class, args);
  }
}
