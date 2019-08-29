package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Create by  sl on 2019-07-31 23:14
 */
@Getter
@Setter
public abstract class AbstractProcessor implements PageProcessor {

    private Site site = CrawelUtils.getSite();

    @Override
    public Site getSite() {
        return site;
    }

    public void start(PageProcessor pageProcessor, String url) {

        Spider.create(pageProcessor)
                .addUrl(url).thread(Constants.THREAD_DEFAULT_NUM).run();
    }
}
