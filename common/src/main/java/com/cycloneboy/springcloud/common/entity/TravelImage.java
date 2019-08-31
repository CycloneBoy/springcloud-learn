package com.cycloneboy.springcloud.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author cycloneboy
 * @since 2019-03-31
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_travel_image")
public class TravelImage extends Model<TravelImage> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private String id;

  @TableField("note_id")
  private String noteId;

  @TableField("image_id")
  private String imageId;

  @TableField("vote_num")
  private Integer voteNum;

  @TableField("reply_num")
  private Integer replyNum;

  @TableField("share_num")
  private Integer shareNum;

  @TableField("original_url")
  private String originalUrl;

  /**
   * 视频名称
   */
  private String name;

  /**
   * 视频分类
   */
  private String category;

  /**
   * 视频封面
   */
  private String cover;

  /**
   * 视频链接
   */
  private String url;


  @Override
  protected Serializable pkVal() {
    return this.id;
  }


}
