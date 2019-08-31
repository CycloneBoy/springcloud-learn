package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.kafka.TravelNoteSenderService;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
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
 * Create by author sl on 2019-07-31 23:12
 */
@Setter
@Getter
@Slf4j
@Component
public class NoteListProcessor implements PageProcessor {


    @Autowired
    private TravelNoteSenderService travelNoteSenderService;

    /**
     * 设置站点信息
     */
    private Site site = CrawelUtils.getSite();

    @Autowired
    private TravelNoteService travelNoteService;

    @Override
    public void process(Page page) {
        List<TravelNote> travelNoteList = new ArrayList<>();

        List<Selectable> selectableList = page.getHtml().xpath("//div[@class='calendar']").nodes();
        selectableList.forEach(selectable -> {
            int year = Integer.parseInt(selectable.xpath("//em[@class='cur-year']/text()").toString());
            int month = Integer.parseInt(selectable.xpath("//em[@class='cur-month']/text()").toString());
            log.info("爬取年份-月份：{} - {}", year, month);

            // 提取游记列表
            List<Selectable> noteSelectableList = selectable.xpath("//li[@class='_j_hover']").nodes();
            noteSelectableList.forEach(selectableNote -> {
                String noteUrl = selectableNote.xpath("span/a/@href").toString();
                String noteImageUrl = selectableNote.xpath("span/a/img/@src").toString();
                int day = Integer.parseInt(selectableNote.xpath("em/text()").toString());

                String destinationName = selectableNote.xpath("span[@class='mark']/a/text()").toString();
                String authorUrl = selectableNote.xpath("span[@class='mark']/a[@class='user']/@href").toString();
                String authorImageUrl = selectableNote.xpath("span[@class='mark']/a[@class='user']/img/@src").toString();
                String authorName = selectableNote.xpath("span[@class='mark']/a[@class='user']/img/@title").toString();

                TravelNote travelNote = new TravelNote();
                travelNote.setYear(year);
                travelNote.setMonth(month);
                travelNote.setDay(day);
                travelNote.setUrl(noteUrl);
                travelNote.setNoteImageUrl(noteImageUrl);
                travelNote.setDestination(destinationName);
                travelNote.setAuthorUrl(authorUrl);
                travelNote.setAuthorImageUrl(authorImageUrl);
                travelNote.setAuthorName(authorName);
                travelNote.setCreateTime(LocalDateTime.now());
                travelNoteList.add(travelNote);
            });


        });

        log.info("总共抓取游记条数： {} from {} ", travelNoteList.size(), page.getRequest().getUrl());
        travelNoteList.forEach(note -> {
            travelNoteSenderService.send(note);
//            log.info(note.toString());
        });

//        travelNoteService.saveBatch(travelNoteList, 50);
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
