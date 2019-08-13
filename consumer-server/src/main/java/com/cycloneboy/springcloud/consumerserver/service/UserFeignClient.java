package com.cycloneboy.springcloud.consumerserver.service;

import com.cycloneboy.springcloud.consumerserver.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "producer-server", fallback = UserRemoteHystrix.class)
public interface UserFeignClient {

    @GetMapping( "/user/{id}")
    User findById(@PathVariable("id") Long id);
}

