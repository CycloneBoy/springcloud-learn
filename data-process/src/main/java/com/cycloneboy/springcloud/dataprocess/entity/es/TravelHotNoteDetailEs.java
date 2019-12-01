package com.cycloneboy.springcloud.dataprocess.entity.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
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
@Document(indexName = "mafengwo_travel_hot_destination_note", type = "_doc", shards = 1, replicas = 0)
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
  @Field(type = FieldType.Text)
  private String resourceType;

  /**
   * 游记链接
   */
  @Field(type = FieldType.Text)
  private String travelUrl;

  /**
   * 游记名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String travelName;

  /**
   * 游记分类 ： 宝藏 、 星级'
   */
  @Field(type = FieldType.Text)
  private String travelType;

  /**
   * 游记摘要
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String travelSummary;

  /**
   * 游记目的地
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String travelDestination;

  /**
   * 游记目的地国家
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String travelDestinationCountry;

  /**
   * 游记封面图片链接
   */
  @Field(type = FieldType.Text)
  private String travelImageUrl;

  /**
   * 游记作者ID
   */
  @Field(type = FieldType.Text)
  private String authorId;

  /**
   * 游记作者首页链接
   */
  @Field(type = FieldType.Text)
  private String authorUrl;

  /**
   * 游记作者名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String authorName;

  /**
   * 游记作者图像链接
   */
  @Field(type = FieldType.Text)
  private String authorImageUrl;

  /**
   * 游记浏览总数
   */
  @Field(type = FieldType.Long)
  private Long travelViewCount;

  /**
   * 游记评论总数
   */
  @Field(type = FieldType.Long)
  private Long travelCommentCount;

  /**
   * 游记顶的总数
   */
  @Field(type = FieldType.Long)
  private Long travelUpCount;

  /**
   * 游记父亲ID
   */
  @Field(type = FieldType.Text)
  private String travelFatherId;

  /**
   * 游记ID
   */
  @Field(type = FieldType.Text)
  private String travelId;

  /**
   * 是否已经爬取
   */
  @Field(type = FieldType.Text)
  private String crawlStatus;

  /**
   * 爬取时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date crawlTime;

  /**
   * 创建时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createDatetime;


}
