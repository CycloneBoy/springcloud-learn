package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.travelnote.domain.ImageInfoRequest;
import com.cycloneboy.springcloud.travelnote.domain.TravelImageRequest;
import com.cycloneboy.springcloud.travelnote.pipeline.TravelImagePipeline;
import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sl
 * @since -
 */
@Getter
@Setter
@Slf4j
@Component
public class TravelImageProcessor  implements PageProcessor {

    public static final String MA_FENG_IMAGE_URL = "http://www.mafengwo.cn/photo/11703/scenery_15790467_1.html";

    public static String image_url_regex = "http://.*?mafengwo.net/.*?";

    public static String url_regex = ".*?/photo/.*?/scenery.*?/.*?.html";


    private TravelImageRequest travelImageRequest;

    @Autowired
    private TravelImagePipeline travelImagePipeline;

    @Autowired
    private ImageInfoProcessor imageInfoProcessor;

    /**
     * 设置站点信息
     */
    private Site site = CrawelUtils.getSite();

    @Override
    public void process(Page page) {
        // 获取所有的链接
        List<String> linkList = page.getHtml().links().all();
        List<String> urlList = new ArrayList<>();

        for (String link : linkList) {
//            log.info("link:" + link);
            // 避免页面重复添加此URL到请求队列
            if (link.matches(url_regex)) {
//                log.info("url_regex match link:" + link);
                urlList.add(link);
            }
        }
        log.info("总共链接：" + linkList.size() + " - 图片链接：" + urlList.size());

        log.info("------------------------------");
        // 图片获取信息地址
//        urlList.forEach(link -> log.info(link));


        // 部分二：定义如何抽取页面信息，并保存下来
        List<String> macthImageUrlList = page.getUrl().regex(url_regex).all();
        List<String> macthImageSrcUrlList = page.getHtml().xpath("//img/@data-original").all();
        List<String> imgSrcUrlList = new ArrayList<>();

        for (String link : macthImageSrcUrlList){
            if(link.matches(image_url_regex)){
//                log.info("url_regex match link:" + link);
                imgSrcUrlList.add(link);
            }
        }
        log.info("总共链接：" + macthImageUrlList.size() + " - 图片链接： " + macthImageSrcUrlList.size() + " - 链接：" + imgSrcUrlList.size());
//        macthImageSrcUrlList.forEach(link -> log.info(link));

        log.info("------------------------------");


        imageInfoProcessor.start(imageInfoProcessor, extractImageInfoRequest(urlList));
//        imgSrcUrlList.forEach(link -> log.info(link));


//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//
//        // 部分三：从页面发现后续的url地址来抓取
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());



//        // 每个页面所属的分类是唯一的
//        String category = page.getHtml()
//                .xpath("//div[@class='programTop']/h1[@style='color: #08a3db;margin-left: 48px;']/text()").get();
//        List<String> coverList = page.getHtml()
//                .xpath("//div[@class='classsifyProgram']/ul/li/a/img[@style]/@src").all();
//        List<String> nameList = page.getHtml().xpath("//span[@class='programName']/text()").all();
//        List<String> urlList = page.getHtml().xpath("//div[@class='classsifyProgram']/ul/li/a/@href").all();
//
//        // 封装Video对象，此处的循环变量也可以用coverList或者urlList的长度，这三者长度是一样的
//        for (int i = 0; i < nameList.size(); i++) {
//            TravelImage travelImage = new TravelImage();
//            travelImage.setCategory(category);
//            travelImage.setName(nameList.get(i));
//            travelImage.setCover(coverList.get(i));
//            travelImage.setUrl(urlList.get(i));
//            // 封装完成后调用DAO层保存数据
//            travelImagePipeline.save(travelImage);
//        }
    }

    private List<ImageInfoRequest> extractImageInfoRequest(List<String> urlList) {
        List<ImageInfoRequest> imageInfoRequestList = new ArrayList<>();
        urlList.forEach(url -> {
            imageInfoRequestList.add(CommonUtils.extractImageInfoFromUrl(url));
        });

        return imageInfoRequestList;
    }

    private String buildUrl(TravelImageRequest request) {
        return "http://www.mafengwo.cn/photo/" + request.getDestinationNumber() + "/scenery_" + request.getNoteNumber() + "_1.html";
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(PageProcessor pageProcessor, TravelImageRequest request) {
        this.setTravelImageRequest(request);
        Spider.create(pageProcessor).
                addUrl(buildUrl(this.getTravelImageRequest())).thread(5).run();
    }
}
