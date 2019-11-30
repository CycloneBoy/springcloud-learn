package com.cycloneboy.springcloud.dataprocess.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Create by  sl on 2019-11-30 13:25
 */
@Data
@Entity
@Table(name = "t_hot_travel_note_list")
public class TravelHotNote {

  /**
   * ID
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * 热门目的地国家名称
   */
  private String countryName;

  /**
   * 热门目的地国家链接
   */
  private String countryUrl;

  /**
   * 热门目的地城市名称
   */
  private String cityName;

  /**
   * 热门目的地城市链接
   */
  private String cityUrl;

  /**
   * 热门目的地城市的编码
   */
  private String cityIndex;

  /**
   * 游记总页数
   */
  private String totalPage;

  /**
   * 游记总数
   */
  private String totalNumber;

  /**
   * 游记图片总数
   */
  private String imageTotalNumber;

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

  /**
   * 热门目的地城市英文名称
   */
  private String cityNameEn;


}
