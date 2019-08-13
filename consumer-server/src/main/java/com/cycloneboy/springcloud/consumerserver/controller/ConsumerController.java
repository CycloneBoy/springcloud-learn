package com.cycloneboy.springcloud.consumerserver.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.consumerserver.entity.User;
import com.cycloneboy.springcloud.consumerserver.service.HelloRemote;
import com.cycloneboy.springcloud.consumerserver.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springclouddemo
 * @description:
 * @author: cycloneboy
 * @create:2018-09-16 15:43
 **/
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private HelloRemote helloRemote;

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private UserFeignClient userFeignClient;
//

//    @RequestMapping("/hello/{name}")
//    public String index(@PathVariable("name") String name){
//        return helloRemote.index(name);
//    }
//
//    @GetMapping("/user/{id}")
//    public User findById(@PathVariable Long id){
//        log.info("get user from remote: {}",id);
////        return restTemplate.getForObject("http://localhost:9003/" + id ,User.class);
//        return userFeignClient.findById(id);
//    }
//
//    @GetMapping("/user")
//    public User findAll(){
//        log.info("get all user from remote");
//        return restTemplate.getForObject("http://localhost:9003/user",User.class);
//    }

//    @GetMapping("/log-instance")
//    public void logUserInstance(){
//        ServiceInstance serviceInstance = loadBalancerClient.choose("consumer-server");
//        log.info("info: {}:{}:{}",serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance.getPort());
//    }

    @GetMapping("/")
    public BaseResponse startCrawlHotNoteList(@RequestParam(name = "year") int year,
                                              @RequestParam(name = "month") int month,
                                              @RequestParam(name = "day") int day) {

        String travelNoteUrl = "http://MAFENGWO/travelnote/?year=" + year + "&month=" + month + "&day=" + day;
        return restTemplate.getForEntity(travelNoteUrl,BaseResponse.class).getBody();
    }
}
