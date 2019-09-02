package com.cycloneboy.springcloud.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_travel_note")
public class TravelNote extends Model<TravelNote> {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "id", type = IdType.AUTO)
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


  @Override
  protected Serializable pkVal() {
    return this.id;
  }

}
