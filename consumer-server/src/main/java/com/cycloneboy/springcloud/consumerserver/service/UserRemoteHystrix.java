package com.cycloneboy.springcloud.consumerserver.service;

import com.cycloneboy.springcloud.consumerserver.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: springclouddemo
 * @description:
 * @author: cycloneboy
 * @create:2018-09-16 17:39
 **/
@Slf4j
@Component
public class UserRemoteHystrix implements  UserFeignClient {

    @Override
    public User findById(@PathVariable("id") Long id) {
        log.warn("user findById failed, " + String.valueOf(id) + ", this message send failed");
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return new User();
    }
}
