package com.cycloneboy.springcloud.travelnote.controller;

import com.cycloneboy.springcloud.travelnote.processor.XiCiProxyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-03 10:20
 */
@RestController
public class ProxyController {

    @Autowired
    private XiCiProxyProcessor xiCiProxyProcessor;

    @GetMapping("/crawlproxy")
    public String startCrawlXiciProxy(@RequestParam(name = "url") String url) {
        xiCiProxyProcessor.start(xiCiProxyProcessor, url);
        return "startCrawlXiciProxy is close!";
    }

}
