package com.cycloneboy.springcloud.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-10 00:37
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String  index(){
        return "index";
    }
}
