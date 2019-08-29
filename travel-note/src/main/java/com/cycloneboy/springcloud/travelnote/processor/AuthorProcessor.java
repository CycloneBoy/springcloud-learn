package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.dao.ProxyMapper;
import com.cycloneboy.springcloud.travelnote.service.ProxyService;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

/**
 * Create by  sl on 2019-08-02 20:45
 */
@Setter
@Getter
@Slf4j
@Component
public class AuthorProcessor implements PageProcessor {

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private ProxyMapper proxyMapper;

    /**
     * 设置站点信息
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setCharset("UTF-8")
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("http://www.mafengwo.cn")
            //添加抓包获取的cookie信息
            .addCookie("__jsluid_h", "c410509a622d04461270e4e3bda23997")
            .addCookie("mfw_uuid", "5d3d7282-ca3e-843b-9988-ed49f7452ce3")
//            .addCookie("oad_n", "a%3A3%3A%7Bs%3A3%3A%22oid%22%3Bi%3A1029%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222019-07-28+18%3A01%3A38%22%3B%7D")
//            .addCookie("__mfwc", "direct")
//            .addCookie("uva", "s%3A91%3A%22a%3A3%3A%7Bs%3A2%3A%22lt%22%3Bi%3A1564308099%3Bs%3A10%3A%22last_refer%22%3Bs%3A23%3A%22http%3A%2F%2Fwww.mafengwo.cn%2F%22%3Bs%3A5%3A%22rhost%22%3BN%3B%7D%22%3B")
//            .addCookie("__mfwurd", "a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1564308099%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D")
//            .addCookie("__mfwuuid", "5d3d7282-ca3e-843b-9988-ed49f7452ce3")
//            .addCookie("UM_distinctid", "16c38075280829-03423db5c72b4d-3f71045b-1fa400-16c38075281976")
//            .addCookie("mafengwo", "03d512cebb1d3f8def099843dc67ccb8_33736599_5d3d728dd476c6.39300375_5d3d728dd476f2.77290633")
//            .addCookie("__omc_chl", "")
//            .addCookie("__omc_r", "")
//            .addCookie("PHPSESSID", "03eitbfun20qpbb203884l5e66")
//            .addCookie("mfw_uid", "33736599")
//            .addCookie("Hm_lvt_8288b2ed37e5bc9b4c9f7008798d2de0", "1564491449,1564585557,1564674144,1564747680")
//            .addCookie("__jsl_clearance", "1564753650.363|0|OOyJUhvrbbMlmUdtbUhyaTvVCRA%3D")
//            .addCookie("CNZZDATA30065558", "cnzz_eid%3D65657079-1564306354-http%253A%252F%252Fwww.mafengwo.cn%252F%26ntime%3D1564750277")
//            .addCookie("uol_throttle", "33736599")
//            .addCookie("__mfwb", "5e733b6329be.1.direct")
//            .addCookie("__mfwa", "1564308099297.51694.14.1564747680196.1564756599972")
//            .addCookie("__mfwlv", "1564756599")
//            .addCookie("__mfwvn", "9")
//            .addCookie("__mfwlt", "1564756599")
//            .addCookie("Hm_lpvt_8288b2ed37e5bc9b4c9f7008798d2de0", "156475660")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的

            .addHeader("User-Agent",
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
            .addHeader("Connection", "keep-alive")
            .addHeader("Referer", "http://www.mafengwo.cn/u/74231765/note.html")
            .addHeader("X-Requested-With", "XMLHttpRequest");

    @Override
    public void process(Page page) {
        log.info(page.getRawText());
//        TravelNoteInfo travelNoteInfo = JSON.parseObject(page.getRawText(), TravelNoteInfo.class);
//        log.info(travelNoteInfo.toString());
//
////        List<String> chinaList = new JsonPathSelector("$.data.pointers.china").selectList(page.getRawText());
////        List<String> worldList = new JsonPathSelector("$.data.pointers.world").selectList(page.getRawText());
//
//        List<AuthorNote> authorNoteList = new ArrayList<>();
////        authorNoteList.addAll(travelNoteInfo.getData().getPointers().getChina());
////        authorNoteList.addAll(travelNoteInfo.getData().getPointers().getChina());
////        chinaList.forEach(note -> {
////            AuthorNote authorNote = JSONObject.parseObject(note, AuthorNote.class);
////            authorNoteList.add(authorNote);
////        });
////
////        worldList.forEach(note -> {
////            AuthorNote authorNote = JSONObject.parseObject(note, AuthorNote.class);
////            authorNoteList.add(authorNote);
////        });
//
//        log.info("爬取大小：" + authorNoteList.size());


    }

    @Override
    public Site getSite() {
        return this.site;
    }


    private String buildUrl(String authorId, boolean ajax) {
        if (ajax) {
            return "http://www.mafengwo.cn/home/ajax_index.php?act=getNoteMapPointer&uid=" + authorId;
        }
        return "http://www.mafengwo.cn/u/" + authorId + "/note.html";
    }

    public void start(PageProcessor pageProcessor, List<String> authorIdList) {

//        QueryWrapper queryWrapper = new QueryWrapper();
//        List<com.cycloneboy.crawler.travelnote.entity.Proxy> proxyList = proxyService.list(queryWrapper);
//
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy(proxyList.get(0).getIp(), proxyList.get(0).getPort())));

        //设置POST请求
        List<Request> requestList = new ArrayList<>();
        authorIdList.forEach(authorId -> {
            Request request = new Request(buildUrl(authorId, false));
            request.setMethod(HttpConstant.Method.GET);
            requestList.add(request);
        });


        for (int index = 0; index < requestList.size(); index++) {
            Spider.create(pageProcessor)
//                    .setDownloader(httpClientDownloader)
                    .addRequest(requestList.get(index))
                    .thread(Constants.THREAD_DEFAULT_NUM).run();
        }

    }
}
