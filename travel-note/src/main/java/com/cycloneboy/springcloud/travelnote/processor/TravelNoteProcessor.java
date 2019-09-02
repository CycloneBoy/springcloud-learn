package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteDetailService;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Create by author sl on 2019-07-31 23:12
 */
@Setter
@Getter
@Slf4j
@Component
public class TravelNoteProcessor implements PageProcessor {


    private static ChromeDriverService service;

    /**
     * 设置站点信息
     */
    private Site site = CrawelUtils.getSite();

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private TravelNoteDetailService travelNoteDetailService;

    @Override
    public void process(Page page) {
        WebDriver driver = CrawelUtils.getPhantomJSDriver();
        driver.get(page.getRequest().getUrl());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div")));

        // 显示搜索结果页面的 title
        log.info(driver.getPageSource());

        WebElement webElement = driver.findElement(By.id("page"));
        String str = webElement.getAttribute("outerHTML");


        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
        service.stop();

        log.info(str);
        log.info(driver.getPageSource());
        AuthorAndNoteList authorAndNoteList = CrawelUtils.extractNoteHtml(str);
        TravelNoteDetail travelNoteDetail = authorAndNoteList.getTravelNoteDetailList().get(0);
        travelNoteDetailService.save(travelNoteDetail);


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
