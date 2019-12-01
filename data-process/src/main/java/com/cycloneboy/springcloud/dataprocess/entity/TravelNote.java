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
 *
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
@Data
@Entity
@Table(name = "t_travel_note")
public class TravelNote implements Serializable {


  private static final long serialVersionUID = 7524303864886077879L;

  /**
   * ID
   */
  @Id
  @GeneratedValue
  private Integer id;

  /**
   * 游记年
   */
  private Integer year;

  /**
   * 游记月
   */
  private Integer month;

  /**
   * 游记日
   */
  private Integer day;

  /**
   * 游记链接
   */
  private String url;

  /**
   * 游记封面链接
   */
  private String noteImageUrl;

  /**
   * 游记目的地
   */
  private String destination;

  /**
   * 作者链接
   */
  private String authorUrl;

  /**
   * 作者名称
   */
  private String authorName;

  /**
   * 游记作者图片链接
   */
  private String authorImageUrl;

  /**
   * 创建时间
   */
  private Date createTime;


  /**
   * 游记类型
   */
  private Integer noteType;
}
