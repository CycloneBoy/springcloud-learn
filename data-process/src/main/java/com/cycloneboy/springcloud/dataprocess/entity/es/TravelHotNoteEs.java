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
 * Create by  sl on 2019-11-30 13:25
 */
@Data
@Document(indexName = "mafengwo_hot_travel_note_list", type = "_doc", shards = 1, replicas = 0)
public class TravelHotNoteEs implements Serializable {

  private static final long serialVersionUID = -2208877279950420470L;
  /**
   * ID
   */
  @Id
  @Field(type = FieldType.Long)
  private Long id;

  /**
   * 热门目的地国家名称
   */
  //	@Field(type = FieldType.String)//1.5.8 spring-boot使用类型
  //1：ik_smart：做最粗粒度的拆分；2：ik_max_word：做最细粒度的拆分
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")//使用ik分词
  private String countryName;

  //必须使用@Field注解，否则使用精确匹配查询字段需要type.Keyword
  //	@Field(type = FieldType.Keyword)
  //	@Field(type = FieldType.String)//1.5.8 spring-boot使用类型
  /**
   * 热门目的地国家链接
   */
  @Field(type = FieldType.Text)
  private String countryUrl;

  /**
   * 热门目的地城市名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String cityName;

  /**
   * 热门目的地城市链接
   */
  @Field(type = FieldType.Text)
  private String cityUrl;

  /**
   * 热门目的地城市的编码
   */
  @Field(type = FieldType.Text)
  private String cityIndex;

  /**
   * 游记总页数
   */
  @Field(type = FieldType.Long)
  private Long totalPage;

  /**
   * 游记总数
   */
  @Field(type = FieldType.Long)
  private Long totalNumber;

  /**
   * 游记图片总数
   */
  @Field(type = FieldType.Long)
  private Long imageTotalNumber;

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
  private String crawlTime;

  /**
   * 创建时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createDatetime;

  /**
   * 热门目的地城市英文名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String cityNameEn;


}
