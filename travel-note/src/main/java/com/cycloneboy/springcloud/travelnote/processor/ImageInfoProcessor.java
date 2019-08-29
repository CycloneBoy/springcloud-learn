package com.cycloneboy.springcloud.travelnote.processor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cycloneboy.springcloud.travelnote.domain.ImageInfoRequest;
import com.cycloneboy.springcloud.travelnote.domain.PhotoInfo;
import com.cycloneboy.springcloud.travelnote.entity.TravelImage;
import com.cycloneboy.springcloud.travelnote.service.TravelImageService;
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
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

@Setter
@Getter
@Slf4j
@Component
public class ImageInfoProcessor implements PageProcessor {

    public static final String IMAGE_URL = "http://www.mafengwo.cn/photo/11703/scenery_15790467/730747855.html";
    public static final String GET_IMAGE_VOTE_URL = "http://www.mafengwo.cn/mdd/photo/ajax_any.php?sAction=getAlbumPhoto&iAlid=756220591&iIid=15658686";


    private String noteNumber;

    @Autowired
    private TravelImageService travelImageService;

    /**
     * 设置站点信息
     */
    private Site site = CrawelUtils.getSite();

    @Override
    public void process(Page page) {

        String url = page.getRequest().getUrl();
        String imageNumber = CommonUtils.extractImageId(url);
        log.info(imageNumber);
        log.info("get page info: " + page.getRawText());
        PhotoInfo photoInfo = extractPhotoInfo(imageNumber, page.getRawText());


        saveNoteImage(photoInfo);


    }

    /**
     * 保存游记图片
     *
     * @param photoInfo
     */
    private void saveNoteImage(PhotoInfo photoInfo) {
        TravelImage travelImage = CommonUtils.convertPhotoInfoToTravelImage(photoInfo);
        TravelImage travelImageOld = travelImageService.getOne(
                new LambdaQueryWrapper<TravelImage>().eq(TravelImage::getImageId, travelImage.getImageId()));
        if (travelImageOld == null) {
            travelImageService.saveOrUpdate(travelImage);
        }

    }

    private PhotoInfo extractPhotoInfo(String imageNumber, String jsonData) {
        String voteNum = new JsonPathSelector("$.payload.photo." + imageNumber + ".vote_num").select(jsonData);
        String replyNum = new JsonPathSelector("$.payload.photo." + imageNumber + ".reply_num").select(jsonData);
        String shareNum = new JsonPathSelector("$.payload.photo." + imageNumber + ".share_num").select(jsonData);
        String originalUrl = new JsonPathSelector("$.payload.photo." + imageNumber + ".original_url").select(jsonData);

        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setNoteNumber(this.noteNumber);
        photoInfo.setImageNumber(imageNumber);
        photoInfo.setVoteNum(Integer.valueOf(voteNum));
        photoInfo.setReplyNum(Integer.valueOf(replyNum));
        photoInfo.setShareNum(Integer.valueOf(shareNum));
        photoInfo.setOriginalUrl(originalUrl);
        return photoInfo;
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String buildUrl(ImageInfoRequest infoRequest) {
        return "http://www.mafengwo.cn/mdd/photo/ajax_any.php?sAction=getAlbumPhoto&iAlid=" + infoRequest.getImageNumber() + "&iIid=" + infoRequest.getNoteNumber();
    }

    public void start(PageProcessor pageProcessor, List<ImageInfoRequest> infoRequestList) {
        this.setNoteNumber(infoRequestList.get(0).getNoteNumber());
        //设置POST请求
        List<Request> requestList = new ArrayList<>();
        infoRequestList.forEach(imageInfoRequest -> {
            Request request = new Request(buildUrl(imageInfoRequest));
            request.setMethod(HttpConstant.Method.GET);
            requestList.add(request);
        });

        for (int index = 0; index < requestList.size(); index++) {
            Spider.create(pageProcessor).addRequest(requestList.get(index))
                    .thread(5).run();
        }

    }
}
