package com.cycloneboy.springcloud.consumerserver.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: springclouddemo
 * @description:
 * @author: cycloneboy
 * @create:2018-09-16 17:39
 **/
@Component
public class HelloRemoteHystrix implements  HelloRemote {

    @Override
    public String index(@RequestParam(value = "name") String name) {
        return "hello, " + name + ", this message send failed";
    }
}
