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
 *
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
@Data
@Document(indexName = "mafengwo_travel_note", type = "_doc", shards = 1, replicas = 0)
public class TravelNoteEs implements Serializable {


  private static final long serialVersionUID = 7524303864886077879L;

  /**
   * ID
   */
  @Id
  @Field(type = FieldType.Integer)
  private Integer id;

  /**
   * 游记年
   */
  @Field(type = FieldType.Integer)
  private Integer year;

  /**
   * 游记月
   */
  @Field(type = FieldType.Integer)
  private Integer month;

  /**
   * 游记日
   */
  @Field(type = FieldType.Integer)
  private Integer day;

  /**
   * 游记链接
   */
  @Field(type = FieldType.Text)
  private String url;

  /**
   * 游记封面链接
   */
  @Field(type = FieldType.Text)
  private String noteImageUrl;

  /**
   * 游记目的地
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")//使用ik分词
  private String destination;

  /**
   * 作者链接
   */
  @Field(type = FieldType.Text)
  private String authorUrl;

  /**
   * 作者名称
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")//使用ik分词
  private String authorName;

  /**
   * 游记作者图片链接
   */
  @Field(type = FieldType.Text)
  private String authorImageUrl;

  /**
   * 创建时间
   */
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;


  /**
   * 游记类型
   */
  @Field(type = FieldType.Integer)
  private Integer noteType;
}
