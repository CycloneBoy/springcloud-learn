package com.cycloneboy.springcloud.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
import org.elasticsearch.common.inject.name.Named;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <p>
 * 马蜂窝热门游记列表
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Data
@Document(indexName = "mafengwo_travel_hot_destination_note_20191213", type = "doc", shards = 1, replicas = 0)
public class TravelHotNoteDetailEs implements Serializable {


  private static final long serialVersionUID = -8360299394324455025L;

  /**
   * id
   */
  @Id
  @Field(type = FieldType.Long)
  private Long id;

  /**
   * 游记爬取来源
   */
  @Field(type = FieldType.Text, normalizer = "resource_type")
//  @Named("resource_type")
  private String resource_type;

  /**
   * 游记链接
   */
  @Field(type = FieldType.Text)
  @Named("travel_url")
  private String travelUrl;

  /**
   * 游记名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  @Named("travel_name")
  private String travelName;

  /**
   * 游记分类 ： 宝藏 、 星级'
   */
  @Field(type = FieldType.Text)
  @Named("travel_type")
  private String travelType;

  /**
   * 游记摘要
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  @Named("travel_summary")
  private String travelSummary;

  /**
   * 游记目的地
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  @Named("travel_destination")
  private String travelDestination;

  /**
   * 游记目的地国家
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  @Named("travel_destination_country")
  private String travelDestinationCountry;

  /**
   * 游记封面图片链接
   */
  @Field(type = FieldType.Text)
  @Named("travel_image_url")
  private String travelImageUrl;

  /**
   * 游记作者ID
   */
  @Field(type = FieldType.Text)
  @Named("author_id")
  private String authorId;

  /**
   * 游记作者首页链接
   */
  @Field(type = FieldType.Text)
  @Named("author_url")
  private String authorUrl;

  /**
   * 游记作者名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  @Named("author_name")
  private String authorName;

  /**
   * 游记作者图像链接
   */
  @Field(type = FieldType.Text)
  @Named("author_image_url")
  private String authorImageUrl;

  /**
   * 游记浏览总数
   */
  @Field(type = FieldType.Long)
  @Named("travel_view_count")
  private Long travelViewCount;

  /**
   * 游记评论总数
   */
  @Field(type = FieldType.Long)
  @Named("travel_comment_count")
  private Long travelCommentCount;

  /**
   * 游记顶的总数
   */
  @Field(type = FieldType.Long)
  @Named("travel_up_count")
  private Long travelUpCount;

  /**
   * 游记父亲ID
   */
  @Field(type = FieldType.Text)
  @Named("travel_father_id")
  private String travelFatherId;

  /**
   * 游记ID
   */
  @Field(type = FieldType.Text)
  @Named("travel_id")
  private String travelId;

  /**
   * 是否已经爬取
   */
  @Field(type = FieldType.Text)
  @Named("crawl_status")
  private String crawlStatus;

  /**
   * 爬取时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Named("crawl_time")
  private Date crawlTime;

  /**
   * 创建时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Named("create_datetime")
  private Date createDatetime;


}
