package com.cycloneboy.springcloud.travelnote.controller;

import static com.cycloneboy.springcloud.common.common.Constants.TRAVEL_NOTE_LIST_BY_YEAR_URL;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.processor.NoteListProcessor;
import com.cycloneboy.springcloud.travelnote.processor.TravelNoteProcessor;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
import com.cycloneboy.springcloud.travelnote.service.crawl.TravelCrawlService;
import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-07-31 23:31
 */
@Slf4j
@RestController
public class TravelNoteController {


    @Autowired
    private NoteListProcessor noteListProcessor;

    @Autowired
    private TravelNoteProcessor travelNoteProcessor;

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private TravelCrawlService travelCrawlService;
    /**
     * 爬取每一年的蜂首游记（历历在目）,热门游记那一年的365篇游记
     *
     * @param url 历历在目游记列表链接： http://www.mafengwo.cn/app/calendar.php?year=2019
     * @return
     */
    @GetMapping("/crawlnote")
    public BaseResponse startCrawlTravel(@RequestParam(name = "url") String url) {
        noteListProcessor.start(noteListProcessor, url);
        return new BaseResponse("startCrawlTravel is close!");
    }

    /**
     * 爬取2019 -2010 年的蜂首游记（历历在目）,热门游记那一年的365篇游记
     * http://www.mafengwo.cn/app/calendar.php?year=2019
     * @return
     */
    @GetMapping("/crawlnote/all")
    public BaseResponse startCrawlTravelAll() {
        String url;
        for (int year = 2019; year >= 2010; year--) {
            url = TRAVEL_NOTE_LIST_BY_YEAR_URL + year;
            noteListProcessor.start(noteListProcessor, url);
        }

        return new BaseResponse("startCrawlTravelAll is close!");
    }

    @GetMapping("/note")
    public BaseResponse startCrawlTravelNote(@RequestParam(name = "url") String url) {
        travelNoteProcessor.start(travelNoteProcessor, url);
        return new BaseResponse("startCrawlTravelNote is close!");
    }

    /**
     * 爬取游记详细信息
     * @param url  游记链接： http://www.mafengwo.cn/i/11895202.html
     * @return 详细游记信息
     * @throws IOException
     */
    @GetMapping("/notebyurl")
    public BaseResponse startCrawlNote(@RequestParam(name = "url") String url) {
        return new BaseResponse(travelCrawlService.processTravelNoteDetail(url));
    }


    /**
     * 爬取游记详细信息 的热门游记的详细信息 爬取2019 -2010 年的蜂首游记（历历在目）,热门游记那一年的365篇游记
     *
     * @param year 年份
     * @return 详细游记信息
     */
    @GetMapping("/notebyurl/hot")
    public BaseResponse startCrawlHotNote(@RequestParam(name = "year") int year) {
        // 查询列表
        QueryWrapper<TravelNote> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelNote::getYear, year);
        List<TravelNote> travelNoteList = travelNoteService.list(queryWrapper);

        // 抓取列表
        for (TravelNote travelNote : travelNoteList) {
            travelCrawlService
                .processTravelNoteDetail(Constants.MAFENGWO_HOST_URL + travelNote.getUrl());
        }

        return new BaseResponse(travelNoteList);
    }


    /**
     *  爬取游记详细信息 的热门游记的详细信息 爬取2019 -2010 年的蜂首游记（历历在目
     *  热门游记 ->
     *              游记信息 + 基本作者信息
     *              作者首页 ->  爬取作者信息 + 游记列表信息
     *              游记列表信息 -> 游记详细信息 + 游记图片信息
     * @param year 游记年份
     * @param month 游记月份
     * @param day 游记天数
     * @return
     */
    @GetMapping("/notebyurl/hot/savelist")
    public BaseResponse startCrawlHotNoteList(@RequestParam(name = "year") int year,
        @RequestParam(name = "month") int month,
        @RequestParam(name = "day") int day) {

        List<TravelNote> travelNoteList = travelNoteService.getNoteList(year, month, day);

        for (TravelNote travelNote : travelNoteList) {
            AuthorAndNoteList authorAndNoteList = travelCrawlService
                .processTravelNoteDetail(Constants.MAFENGWO_HOST_URL + travelNote.getUrl());
            travelCrawlService.processTravelNoteAuthor(
                CommonUtils.buildTravelAuthorUrl(authorAndNoteList.getNoteAuthor().getUid()));
        }

        return new BaseResponse(travelNoteList);
    }


    /**
     * 抓取游记作者的首页
     * @param url 作者home链接
     * @return
     */
    @GetMapping("/note/author/home")
    public BaseResponse startCrawlAuthorHome(@RequestParam(name = "url") String url) {

        return new BaseResponse(travelCrawlService.processTravelNoteAuthor(url));
    }


    /**
     * 抓取游记作者的首页
     * @param body 作者home链接
     * @return
     */
    @PostMapping("/note/author/home/html")
    public BaseResponse startCrawlAuthorHomeByBody(String body) {

        return new BaseResponse(travelCrawlService.processTravelNoteAuthorByHtml(body));
    }

}
