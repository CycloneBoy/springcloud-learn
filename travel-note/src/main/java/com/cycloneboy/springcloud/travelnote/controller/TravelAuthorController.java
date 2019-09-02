package com.cycloneboy.springcloud.travelnote.controller;

import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorNoteListRequest;
import com.cycloneboy.springcloud.travelnote.processor.AuthorNoteProcessor;
import com.cycloneboy.springcloud.travelnote.processor.AuthorProcessor;
import com.cycloneboy.springcloud.travelnote.service.NoteAuthorService;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteDetailService;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-08-02 21:43
 */
@Slf4j
@RestController
public class TravelAuthorController {

    private String TravelAuthorUrl = "http://www.mafengwo.cn/u/74231765/note.html";

    @Autowired
    private AuthorProcessor authorProcessor;

    @Autowired
    private AuthorNoteProcessor authorNoteProcessor;


    @Autowired
    private NoteAuthorService noteAuthorService;

    @Autowired
    private TravelNoteDetailService travelNoteDetailService;


    @GetMapping("/crawlauthor")
    public String startCrawlAuthor(@RequestParam(name = "url") String url) {
        authorProcessor.start(authorProcessor, Arrays.asList(url));
        return "startCrawlAuthor is close!";
    }

    @GetMapping("/note/author")
    public String startCrawlAuthorNote(@RequestParam(name = "url") String url) {
        String html = CrawelUtils.getHtmlFromUrl(url);
        AuthorAndNoteList authorAndNoteList = CrawelUtils.extractAuthorNoteList(html);

        noteAuthorService.save(authorAndNoteList.getNoteAuthor());
        List<TravelNoteDetail> travelNoteDetailList = authorAndNoteList.getTravelNoteDetailList();
        travelNoteDetailList.forEach(travelNoteDetail -> {
            travelNoteDetail.setAuthorId(authorAndNoteList.getNoteAuthor().getId());
        });
        travelNoteDetailService.saveBatch(travelNoteDetailList);
        return html;
    }

    @GetMapping("/note/author/list")
    public String startCrawlAuthorNotePage(@RequestParam(name = "url") String url,
                                           @RequestParam(name = "page") int page) throws IOException {
        String html = CrawelUtils.getHtmlNoteListFromUrl(url, page);
        AuthorAndNoteList authorAndNoteList = CrawelUtils.extractAuthorNoteList(html);

        noteAuthorService.save(authorAndNoteList.getNoteAuthor());
        List<TravelNoteDetail> travelNoteDetailList = authorAndNoteList.getTravelNoteDetailList();
        travelNoteDetailList.forEach(travelNoteDetail -> {
            travelNoteDetail.setAuthorId(authorAndNoteList.getNoteAuthor().getId());
        });
        travelNoteDetailService.saveBatch(travelNoteDetailList);

        return html;
    }


    @PostMapping("/note/author")
    public String startCrawlAuthorNote(@RequestBody AuthorNoteListRequest request) {

        authorNoteProcessor.start(authorNoteProcessor, request);
        return "startCrawlAuthorNote is close!";
    }
}
