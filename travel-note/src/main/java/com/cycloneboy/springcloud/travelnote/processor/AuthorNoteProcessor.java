package com.cycloneboy.springcloud.travelnote.processor;

import com.cycloneboy.springcloud.travelnote.common.Constants;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorNoteListRequest;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

/**
 * Create by  sl on 2019-08-03 23:52
 */
@Setter
@Getter
@Slf4j
@Component
public class AuthorNoteProcessor implements PageProcessor {

    private static final String AUTHOR_NOTE_LIST_URL = "http://www.mafengwo.cn/wo/ajax_post.php";
    /**
     * 设置站点信息
     */
    private Site site = CrawelUtils.getSite();

    @Override
    public void process(Page page) {
        log.info(page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String buildUrl(AuthorNoteListRequest request) {
        return AUTHOR_NOTE_LIST_URL + "?sAction=getArticle&iPage=" + request.getIPage() + "&iUid=" + request.getIUid();
    }


    public void start(PageProcessor pageProcessor, AuthorNoteListRequest authorNoteListRequest) {
        Request request = new Request(buildUrl(authorNoteListRequest));
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("sAction", authorNoteListRequest.getSAction());
        params.put("iPage", authorNoteListRequest.getIPage());
        params.put("iUid", authorNoteListRequest.getIUid());

        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));

        Spider.create(pageProcessor)
                .addRequest(request)
                .thread(Constants.THREAD_DEFAULT_NUM).run();
    }
}
