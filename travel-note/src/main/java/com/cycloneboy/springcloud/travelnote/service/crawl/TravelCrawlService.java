package com.cycloneboy.springcloud.travelnote.service.crawl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cycloneboy.springcloud.common.entity.NoteAuthor;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.domain.TravelImageRequest;
import com.cycloneboy.springcloud.travelnote.kafka.TravelAuthorSenderService;
import com.cycloneboy.springcloud.travelnote.kafka.TravelNoteDetailSenderService;
import com.cycloneboy.springcloud.travelnote.processor.TravelImageProcessor;
import com.cycloneboy.springcloud.travelnote.service.NoteAuthorService;
import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 爬取游记详情、作者首页游记、游记图片链接 Create by  sl on 2019-09-01 14:11
 */
@Slf4j
@Service
public class TravelCrawlService {

  @Autowired
  private NoteAuthorService noteAuthorService;

  @Autowired
  private TravelNoteDetailSenderService travelNoteDetailSenderService;

  @Autowired
  private TravelImageProcessor travelImageProcessor;

  @Autowired
  private TravelAuthorSenderService travelAuthorSenderService;

  /**
   * 根据游记链接来抓取游记,保存到 1: t_travel_note_detail 2: 通过kafka 发送出去
   *
   * @param url 游记链接
   * @return travelNoteDetail
   */
  public AuthorAndNoteList processTravelNoteDetail(String url) {
    String html = CrawelUtils.getHtmlFromUrl(url);

    return processAuthorAndNoteList(CrawelUtils.extractNoteHtml(html));
  }


  /**
   * 根据游记作者首页链接来抓取游记 保存游记作者的信息和作者的第一页游记
   *
   * @param url 游记作者首页链接  : http://www.mafengwo.cn/u/5328159/note.html
   * @return
   */
  public AuthorAndNoteList processTravelNoteAuthor(String url) {
    String html = CrawelUtils.getHtmlFromUrl(url);

    return processTravelNoteAuthorByHtml(html);
  }

  /**
   * 根据游记作者首页 HTML来抓取游记列表
   *
   * @param body
   * @return
   */
  public AuthorAndNoteList processTravelNoteAuthorByHtml(String body) {
    return processAuthorAndNoteList(CrawelUtils.extractAuthorNoteList(body));
  }


  public AuthorAndNoteList processAuthorAndNoteList(AuthorAndNoteList authorAndNoteList) {
    if (CollectionUtils.isEmpty(authorAndNoteList.getTravelNoteDetailList())) {
      return new AuthorAndNoteList();
    }
    NoteAuthor noteAuthor = saveNoteAuthor(authorAndNoteList.getNoteAuthor());
    authorAndNoteList.setNoteAuthor(noteAuthor);
    log.info("保存作者信息：{}", noteAuthor.toString());

    // 发送游记作者
    travelAuthorSenderService.send(noteAuthor);

    authorAndNoteList.getTravelNoteDetailList().forEach(travelNoteDetail -> {
      travelNoteDetail.setAuthorId(noteAuthor.getId());
      // 发送游记详情
      travelNoteDetailSenderService.send(travelNoteDetail);
      saveNoteImage(travelNoteDetail.getDestinationId(), travelNoteDetail.getNoteId());
      log.info("保存热门游记信息： {} - {} ,游记图片总数：{} ", travelNoteDetail.getNoteId(),
          travelNoteDetail.getNoteName(), travelNoteDetail.getImageCount());
    });
//    log.info(authorAndNoteList.getNoteAuthor().toString());
//    travelNoteDetailService.save(travelNoteDetail);
//    noteAuthorService.save(authorAndNoteList.getNoteAuthor());
    return authorAndNoteList;
  }

  /**
   * 保存热门游记的图片信息
   */
  public String saveNoteImage(Integer destinationId, Integer noteId) {
    if (destinationId == null || noteId == null) {
      return "";
    }
    log.info("保存热门游记的图片信息:" + CommonUtils.buildTravelNoteImageUrl(destinationId, noteId));
    TravelImageRequest request = new TravelImageRequest();
    request.setNoteNumber(noteId.toString());
    request.setDestinationNumber(destinationId.toString());

    travelImageProcessor.start(travelImageProcessor, request);
    return request.toString();
  }


  /**
   * 保存作者游记
   *
   * @param noteAuthor 要保存的游记
   */
  public NoteAuthor saveNoteAuthor(NoteAuthor noteAuthor) {
    NoteAuthor noteAuthorOld = noteAuthorService.getOne(
        new LambdaQueryWrapper<NoteAuthor>().eq(NoteAuthor::getUid, noteAuthor.getUid()));

    if (noteAuthorOld == null) {
      noteAuthorService.save(noteAuthor);
      return noteAuthor;
    }
    BeanUtils.copyProperties(noteAuthor, noteAuthorOld);
    noteAuthorService.saveOrUpdate(noteAuthorOld);

    return noteAuthorOld;
  }

}
