package com.cycloneboy.springcloud.travelnote.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.domain.TravelImageRequest;
import com.cycloneboy.springcloud.travelnote.entity.NoteAuthor;
import com.cycloneboy.springcloud.travelnote.kafka.TravelNoteDetailSenderService;
import com.cycloneboy.springcloud.travelnote.processor.ImageInfoProcessor;
import com.cycloneboy.springcloud.travelnote.processor.NoteListProcessor;
import com.cycloneboy.springcloud.travelnote.processor.TravelImageProcessor;
import com.cycloneboy.springcloud.travelnote.processor.TravelNoteProcessor;
import com.cycloneboy.springcloud.travelnote.service.NoteAuthorService;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteDetailService;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-07-31 23:31
 */
@Slf4j
@RestController
public class TravelNoteController {

    private String TravelNoteListUrl = "http://www.mafengwo.cn/app/calendar.php?year=";

    @Autowired
    private NoteListProcessor noteListProcessor;

    @Autowired
    private TravelNoteProcessor travelNoteProcessor;

    @Autowired
    private TravelNoteDetailService travelNoteDetailService;

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private NoteAuthorService noteAuthorService;

    @Autowired
    private ImageInfoProcessor imageInfoProcessor;


    @Autowired
    private TravelImageProcessor travelImageProcessor;

    @Autowired
    private TravelNoteDetailSenderService travelNoteDetailSenderService;


    /**
     * 爬去每一年的蜂首游记（历历在目）,热门游记那一年的365篇游记
     *
     * @param url 历历在目游记列表链接： http://www.mafengwo.cn/app/calendar.php?year=2019
     * @return
     */
    @GetMapping("/crawlnote")
    public String startCrawlTravel(@RequestParam(name = "url") String url) {
        noteListProcessor.start(noteListProcessor, url);
        return "startCrawlTravel is close!";
    }

    /**
     * 爬取2019 -2010 年的蜂首游记（历历在目）,热门游记那一年的365篇游记
     * http://www.mafengwo.cn/app/calendar.php?year=2019
     * @return
     */
    @GetMapping("/crawlnote/all")
    public String startCrawlTravelAll() {
        String url;
        for (int year = 2019; year >= 2010; year--) {
            url = TravelNoteListUrl + year;
            noteListProcessor.start(noteListProcessor, url);
        }

        return "startCrawlTravelAll is close!";
    }

    @GetMapping("/note")
    public String startCrawlTravelNote(@RequestParam(name = "url") String url) {
        travelNoteProcessor.start(travelNoteProcessor, url);
        return "startCrawlTravelNote is close!";
    }

    /**
     * 爬取游记信息
     * @param url  游记链接： http://www.mafengwo.cn/i/11895202.html
     * @return
     * @throws IOException
     */
    @GetMapping("/notebyurl")
    public BaseResponse startCrawlNote(@RequestParam(name = "url") String url) throws IOException {
        String html = CrawelUtils.getHtmlFromUrl(url);

        TravelNoteDetail travelNoteDetail = CrawelUtils.extractNoteHtml(html);
        travelNoteDetailSenderService.send(travelNoteDetail);
        travelNoteDetailService.save(travelNoteDetail);

        return new BaseResponse(travelNoteDetail);
    }


    @GetMapping("/notebyurl/hot")
    public String startCrawlHotNote(@RequestParam(name = "year") int year) throws IOException {
        QueryWrapper<TravelNote> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelNote::getYear, year);
        List<TravelNote> travelNoteList = travelNoteService.list(queryWrapper);

        for (TravelNote travelNote : travelNoteList) {
            String html = null;
            try {
                html = CrawelUtils.getHtmlFromUrl(Constants.MAFENGWO_HOST_URL + travelNote.getUrl());
                TravelNoteDetail travelNoteDetail = CrawelUtils.extractNoteHtml(html);
                travelNoteDetailService.save(travelNoteDetail);

            } catch (Exception e) {
                e.printStackTrace();
                log.info(travelNote.toString());
            }
        }


        return JSON.toJSONString(travelNoteList);
    }


    @GetMapping("/notebyurl/hot/savelist")
    public String startCrawlHotNoteList(@RequestParam(name = "year") int year,
                                        @RequestParam(name = "month") int month,
                                        @RequestParam(name = "day") int day) throws IOException {
        QueryWrapper<TravelNote> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelNote::getYear, year);

        if (month > 0) {
            queryWrapper.lambda().eq(TravelNote::getMonth, month);
        }

        if (day > 0) {
            queryWrapper.lambda().eq(TravelNote::getDay, day);
        }

        List<TravelNote> travelNoteList = travelNoteService.list(queryWrapper);

        for (TravelNote travelNote : travelNoteList) {
            String html = null;
            try {
                TravelNoteDetail travelNoteDetail = saveNoteDetail(travelNote.getUrl());

                // 保存游记作者
                AuthorAndNoteList authorAndNoteList = saveAuthorAndNoteList(travelNoteDetail.getUid());

                // 保存作者那一页游记的信息和图片
                log.info("保存作者那一页游记的信息和图片:" + authorAndNoteList.toString());
                authorAndNoteList.getTravelNoteDetailList().forEach(travelNoteDetail1 -> {
                    try {
                        saveNoteDetail(travelNoteDetail1.getUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                log.info(travelNote.toString());
            }
        }


        return JSON.toJSONString(travelNoteList);
    }

    /**
     * 保存热门游记列表
     *
     * @param hotNoteUrl
     * @return
     * @throws IOException
     */
    private TravelNoteDetail saveNoteDetail(String hotNoteUrl) throws IOException {
        String url = Constants.MAFENGWO_HOST_URL + hotNoteUrl;
        String html = CrawelUtils.getHtmlFromUrl(url);
        TravelNoteDetail travelNoteDetail = CrawelUtils.extractNoteHtml(html);
        saveTravelNoteDetail(travelNoteDetail);
        log.info("保存热门游记信息：" + travelNoteDetail.toString());
        // 保存热门游记的图片信息
        saveNoteImage(travelNoteDetail.getDestinationId(), travelNoteDetail.getNoteId());
        log.info("保存热门游记图片信息：" + travelNoteDetail.getImageCount());
        return travelNoteDetail;
    }

    /**
     * 保存游记
     *
     * @param travelNoteDetail
     */
    private void saveTravelNoteDetail(TravelNoteDetail travelNoteDetail) {

        TravelNoteDetail travelNoteDetailOld = travelNoteDetailService.getOne(
                new LambdaQueryWrapper<TravelNoteDetail>().eq(TravelNoteDetail::getNoteId, travelNoteDetail.getNoteId()));

        if (travelNoteDetailOld != null) {
//            BeanUtils.copyProperties(travelNoteDetailOld, travelNoteDetail);
            travelNoteDetail.setId(travelNoteDetailOld.getId());
            travelNoteDetail.setAuthorId(travelNoteDetailOld.getAuthorId());
            travelNoteDetail.setShortContent(travelNoteDetailOld.getShortContent());
            travelNoteDetail.setImageUrl(travelNoteDetailOld.getImageUrl());
        }

        travelNoteDetailService.saveOrUpdate(travelNoteDetail);
        log.info("保存详细游记信息：" + travelNoteDetail.toString());
    }

    /**
     * 保存游记作者的信息和作者的第一页游记
     *
     * @param authorId http://www.mafengwo.cn/u/5328159.html
     * @return
     * @throws IOException
     */
    private AuthorAndNoteList saveAuthorAndNoteList(Long authorId) throws IOException {
        String url = "http://www.mafengwo.cn/u/" + authorId + "/note.html";
        String html = CrawelUtils.getHtmlFromUrl(url);
        AuthorAndNoteList authorAndNoteList = CrawelUtils.extractAuthorNoteList(html);

        NoteAuthor noteAuthor = saveNoteAuthor(authorAndNoteList.getNoteAuthor());
        log.info("保存作者信息：" + noteAuthor.toString());

        List<TravelNoteDetail> travelNoteDetailList = authorAndNoteList.getTravelNoteDetailList();
        travelNoteDetailList.forEach(travelNoteDetail -> {
            travelNoteDetail.setAuthorId(noteAuthor.getId());
            saveTravelNoteDetail(travelNoteDetail);
            log.info("保存作者游记信息：" + travelNoteDetail.toString());
        });


//        travelNoteDetailService.saveOrUpdateBatch(travelNoteDetailList);

        return authorAndNoteList;
    }

    /**
     * 保存作者游记
     *
     * @param noteAuthor
     */
    private NoteAuthor saveNoteAuthor(NoteAuthor noteAuthor) {
        NoteAuthor noteAuthorOld = noteAuthorService.getOne(
                new LambdaQueryWrapper<NoteAuthor>().eq(NoteAuthor::getUid, noteAuthor.getUid()));
        if (noteAuthorOld == null) {
            noteAuthorService.saveOrUpdate(noteAuthor);
        }


        return noteAuthor;
    }


    /**
     * 保存热门游记的图片信息
     */
    private String saveNoteImage(Integer destinationId, Integer noteId) {
        String url = "http://www.mafengwo.cn/photo/" + destinationId + "/scenery_" + noteId + "_1.html";
        log.info("保存热门游记的图片信息:" + url);
        TravelImageRequest request = new TravelImageRequest();
        request.setNoteNumber(noteId.toString());
        request.setDestinationNumber(destinationId.toString());

        travelImageProcessor.start(travelImageProcessor, request);
        return request.toString();
    }
}
