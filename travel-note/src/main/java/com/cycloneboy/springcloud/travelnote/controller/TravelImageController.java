package com.cycloneboy.springcloud.travelnote.controller;


import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.travelnote.domain.ImageInfoRequest;
import com.cycloneboy.springcloud.travelnote.domain.TravelImageRequest;
import com.cycloneboy.springcloud.travelnote.entity.TravelImage;
import com.cycloneboy.springcloud.travelnote.processor.ImageInfoProcessor;
import com.cycloneboy.springcloud.travelnote.processor.TravelImageProcessor;
import com.cycloneboy.springcloud.travelnote.service.TravelImageService;
import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sl
 * @since -
 */
@RestController
public class TravelImageController {

    @Autowired
    private TravelImageProcessor travelImageProcessor;

    @Autowired
    private ImageInfoProcessor imageInfoProcessor;

    @Autowired
    private TravelImageService travelImageService;


    /**
     * 从图片列表中爬去图片的信息
     *
     * @param url http://www.mafengwo.cn/photo/11703/scenery_15790467/730747855.html
     * @return
     */
    @GetMapping("/crawltravel")
    public String startCrawlTravel(@RequestParam(name = "url") String url) {
        TravelImageRequest request = CommonUtils.extractTravelImageRequestFromUrl(url);
        travelImageProcessor.start(travelImageProcessor, request);
        return "startCrawlTravel is close!";
    }


    @GetMapping("/start")
    public String start(TravelImageRequest request) {
        travelImageProcessor.start(travelImageProcessor, request);
        return "travelImageProcessor is close!";
    }

    @GetMapping("/startimage")
    public String startImageInfo(ImageInfoRequest request) {
        imageInfoProcessor.start(imageInfoProcessor, Arrays.asList(request));
        return "imageInfoProcessor is close!";
    }

    /**
     * 从图片列表链接中爬取单页图片的信息
     *
     * @param url
     * @return
     */
    @GetMapping("/crawlimage")
    public String startCrawlImage(@RequestParam(name = "url") String url) {
        ImageInfoRequest request = CommonUtils.extractImageInfoFromUrl(url);
        imageInfoProcessor.start(imageInfoProcessor, Arrays.asList(request));
        return "startCrawlImage is close!";
    }


    @PostMapping("/saveimage")
    public BaseResponse saveImage(@RequestBody TravelImage travelImage){

        return new BaseResponse();
//        return travelImageService.save(travelImage);
    }
}
