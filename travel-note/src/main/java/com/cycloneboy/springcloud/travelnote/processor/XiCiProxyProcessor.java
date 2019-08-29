package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.domain.proxy.XiciProxy;
import com.cycloneboy.springcloud.travelnote.entity.Proxy;
import com.cycloneboy.springcloud.travelnote.service.ProxyService;
import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import com.cycloneboy.springcloud.travelnote.utils.ProxyUtils;
import com.cycloneboy.springcloud.travelnote.utils.TimeUtils;
import java.time.LocalDateTime;
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
import us.codecraft.webmagic.selector.Selectable;

/**
 * Create by  sl on 2019-08-03 10:06
 */
@Setter
@Getter
@Slf4j
@Component
public class XiCiProxyProcessor implements PageProcessor {

    @Autowired
    private ProxyService proxyService;


    Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("https://www.xicidaili.com")
            .addHeader("User-Agent",
                    "ozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
            .addHeader("Connection", "keep-alive")
            .addHeader("Referer", "https://www.xicidaili.com");

    @Override
    public void process(Page page) {
        List<XiciProxy> xiciProxyList = new ArrayList<>();
        List<Proxy> proxyList = new ArrayList<>();

        List<Selectable> selectableList = page.getHtml().xpath("//table[@id='ip_list']/tbody/tr").nodes();
        selectableList.remove(0);
        selectableList.forEach(selectable -> {
            String ip = selectable.xpath("//td[2]/text()").toString();
            String portStr = selectable.xpath("//td[3]/text()").toString();
            Integer port = portStr == null ? 0 : Integer.parseInt(portStr);

            String protocol = selectable.xpath("//td[6]/text()").toString();
            String speedStr = selectable.xpath("//td[7]/div/@title").toString();
            Float speed = CommonUtils.parseSecond(speedStr);

            String timeStr = selectable.xpath("//td[8]/div/@title").toString();
            Float time = CommonUtils.parseSecond(timeStr);

            String liveTimeStr = selectable.xpath("//td[9]/text()").toString();
            Integer liveTime = CommonUtils.parseTimeStr(liveTimeStr);

            String checkTimeStr = selectable.xpath("//td[10]/text()").toString();
            LocalDateTime checkTime = TimeUtils.parseDateTime(checkTimeStr, TimeUtils.DEFAULT_TIME_FORMAT_NO_SECOND);
            XiciProxy xiciProxy = new XiciProxy(ip, port, protocol, speed, time, liveTime, checkTime);
            log.info(xiciProxy.toString());
            xiciProxyList.add(xiciProxy);

            Proxy proxy = new Proxy();
            proxy.setType(Constants.PORXY_TYPE_XICI);
            proxy.setIp(ip);
            proxy.setPort(port);
            proxy.setProtocol(protocol);
            proxy.setSpeed(speed);
            proxy.setTime(time);
            proxy.setLiveTime(liveTime);
            proxy.setCheckTime(TimeUtils.parseDateTime(checkTimeStr, TimeUtils.DEFAULT_TIME_FORMAT_NO_SECOND));
            proxyList.add(proxy);

        });

        log.info("代理多少；" + xiciProxyList.size());

        List<Proxy> validateProxyList = checkProxyLive(proxyList);

        log.info("验证过的代理多少；" + validateProxyList.size());
        validateProxyList.forEach(proxy -> log.info(proxy.toString()));
        proxyService.saveBatch(validateProxyList);
    }


    /**
     * 检查代理是否可用
     */
    private List<Proxy> checkProxyLive(List<Proxy> proxyList) {
        List<Proxy> proxyLiveList = new ArrayList<>();
        proxyList.forEach(proxy -> {
            Boolean isOk = ProxyUtils.checkProxyIp(proxy, Constants.MAFENGWO_HOST_URL);
            if (isOk) {
                proxyLiveList.add(proxy);
            }

        });

        return proxyLiveList;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(PageProcessor pageProcessor, String url) {

        Spider.create(pageProcessor)
                .addUrl(url).thread(Constants.THREAD_DEFAULT_NUM).run();
    }
}
