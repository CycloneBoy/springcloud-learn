package com.cycloneboy.springcloud.travelnote.utils;

import static com.cycloneboy.springcloud.travelnote.common.Constants.DAY_CN;
import static com.cycloneboy.springcloud.travelnote.common.Constants.HOUR_CN;
import static com.cycloneboy.springcloud.travelnote.common.Constants.MINUTE_CN;
import static com.cycloneboy.springcloud.travelnote.common.Constants.SECOND_CN;

import com.cycloneboy.springcloud.common.entity.TravelImage;
import com.cycloneboy.springcloud.travelnote.domain.ImageInfoRequest;
import com.cycloneboy.springcloud.travelnote.domain.PhotoInfo;
import com.cycloneboy.springcloud.travelnote.domain.TravelImageRequest;

public class CommonUtils {


    public static TravelImage convertPhotoInfoToTravelImage(PhotoInfo photoInfo) {
        TravelImage travelImage = new TravelImage();

        travelImage.setNoteId(photoInfo.getNoteNumber());
        travelImage.setImageId(photoInfo.getImageNumber());
        travelImage.setOriginalUrl(photoInfo.getOriginalUrl());
        travelImage.setReplyNum(photoInfo.getReplyNum());
        travelImage.setVoteNum(photoInfo.getVoteNum());
        travelImage.setShareNum(photoInfo.getShareNum());

        return travelImage;
    }


    /**
     * 从图片url中提取imageNumber
     *
     * @param url "http://www.mafengwo.cn/photo/11703/scenery_15790467/796108384.html";
     * @return
     */
    public static String extractImageId(String url) {
        return url.substring(url.lastIndexOf("iAlid=") + 6, url.lastIndexOf("&iIid="));
    }


    /**
     * 从图片url中提取imageNumber
     *
     * @param url "http://www.mafengwo.cn/photo/11703/scenery_15790467/796108384.html";
     * @return
     */
    public static String extractImageIdFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
    }

    /**
     * 从图片url中提取NoteNumber
     *
     * @param url "http://www.mafengwo.cn/photo/11703/scenery_15790467/796108384.html";
     * @return
     */
    public static String extractNoteIdFromUrl(String url) {
        return url.substring(url.lastIndexOf("scenery_") + 8, url.lastIndexOf("/"));
    }

    /**
     * 从图片url中提取NoteNumber和imageNumber
     *
     * @param url
     * @return
     */
    public static ImageInfoRequest extractImageInfoFromUrl(String url) {
        String noteNumber = extractNoteIdFromUrl(url);
        String imageNumber = extractImageIdFromUrl(url);
        return new ImageInfoRequest(noteNumber, imageNumber);
    }

    /**
     * 从图片url中提取NoteNumber和imageNumber
     *
     * @param url http://www.mafengwo.cn/photo/11703/scenery_15790467/730747855.html
     * @return
     */
    public static TravelImageRequest extractTravelImageRequestFromUrl(String url) {
        TravelImageRequest request = new TravelImageRequest();
        request.setDestinationNumber(url.substring(url.lastIndexOf("photo/") + 6, url.lastIndexOf("/scenery_")));
        request.setNoteNumber(extractNoteIdFromUrl(url));
        return request;
    }

    /**
     * 转换时间 秒
     */
    public static Integer parseTimeStr(String timeStr) {
        if (timeStr.contains(SECOND_CN)) {
            return Integer.parseInt(timeStr.substring(0, timeStr.lastIndexOf(SECOND_CN)));
        }

        if (timeStr.contains(MINUTE_CN)) {
            return Integer.parseInt(timeStr.substring(0, timeStr.lastIndexOf(MINUTE_CN))) * 60;
        }
        if (timeStr.contains(HOUR_CN)) {
            return Integer.parseInt(timeStr.substring(0, timeStr.lastIndexOf(HOUR_CN))) * 3600;
        }
        if (timeStr.contains(DAY_CN)) {
            return Integer.parseInt(timeStr.substring(0, timeStr.lastIndexOf(DAY_CN))) * 3600 * 24;
        }
        return 0;
    }

    public static Float parseSecond(String time) {
        return time == null ? 1000.0f : Float.parseFloat(time.substring(0, time.lastIndexOf(SECOND_CN)));
    }

    /**
     * 根据作者ID返回作者home首页链接
     *
     * @param authorId 作者ID
     * @return 作者home首页链接
     */
    public static String buildTravelAuthorUrl(Long authorId) {
        return "http://www.mafengwo.cn/u/" + authorId + "/note.html";
    }

    /**
     * 根据作者ID返回作者home首页链接
     *
     * @param url 作者游记主页  http://www.mafengwo.cn/u/74369556.html http://www.mafengwo.cn/u/74369556/note.html
     * @return 作者home首页链接
     */
    public static String buildTravelAuthorUrl(String url) {
        if (url != null && url.contains("/note.html")) {
            return url;
        }
        url = url != null ? url.replace(".html", "/note.html") : "";
        return url;
    }

    /**
     * 根据游记目的地ID 和 游记ID 返回 游记图片列表 链接
     *
     * @param destinationId 游记目的地ID
     * @param noteId        游记ID
     * @return 游记图片列表 链接
     */
    public static String buildTravelNoteImageUrl(Integer destinationId, Integer noteId) {
        return "http://www.mafengwo.cn/photo/" + destinationId + "/scenery_" + noteId + "_1.html";
    }
}
