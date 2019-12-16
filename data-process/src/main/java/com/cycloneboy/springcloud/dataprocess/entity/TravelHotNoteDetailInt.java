package com.cycloneboy.springcloud.dataprocess.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * <p>
 * 马蜂窝热门游记列表 马蜂窝热门景点的热门游记列表
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Data
@Entity
@Table(name = "t_travel_hot_destination_note_1")
public class TravelHotNoteDetailInt implements Serializable {


  private static final long serialVersionUID = 260562129392537893L;
  /**
   * id
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * 游记爬取来源
   */
  private String resourceType;

  /**
   * 游记链接
   */
  private String travelUrl;

  /**
   * 游记名称
   */
  private String travelName;

  /**
   * 游记分类 ： 宝藏 、 星级'
   */
  private String travelType;

  /**
   * 游记摘要
   */
  private String travelSummary;

  /**
   * 游记目的地
   */
  private String travelDestination;

  /**
   * 游记目的地国家
   */
  private String travelDestinationCountry;

  /**
   * 游记封面图片链接
   */
  private String travelImageUrl;

  /**
   * 游记作者ID
   */
  private String authorId;

  /**
   * 游记作者首页链接
   */
  private String authorUrl;

  /**
   * 游记作者名称
   */
  private String authorName;

  /**
   * 游记作者图像链接
   */
  private String authorImageUrl;

  /**
   * 游记浏览总数
   */
  private Integer travelViewCount;

  /**
   * 游记评论总数
   */
  private Integer travelCommentCount;

  /**
   * 游记顶的总数
   */
  private Integer travelUpCount;

  /**
   * 游记父亲ID
   */
  private String travelFatherId;

  /**
   * 游记ID
   */
  private String travelId;

  /**
   * 是否已经爬取
   */
  private String crawlStatus;

  /**
   * 爬取时间
   */
  private String crawlTime;

  /**
   * 创建时间
   */
  private Date createDatetime;


}
