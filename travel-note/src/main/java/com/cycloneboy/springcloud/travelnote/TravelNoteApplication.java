package com.cycloneboy.springcloud.travelnote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.cycloneboy.springcloud.travelnote.dao.*")
@EnableDiscoveryClient
@SpringBootApplication
public class TravelNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelNoteApplication.class, args);
    }

}
