package com.cycloneboy.springcloud.travelnote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 马蜂窝作者
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_travel_note_detail")
public class TravelNoteDetail extends Model<TravelNoteDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游记作者编号
     */
    private Long uid;

    /**
     * 游记作者ID
     */
    private Long authorId;

    /**
     * 游记编号
     */
    private Integer noteId;

    /**
     * 游记地点编号
     */
    private Integer destinationId;

    /**
     * 游记链接
     */
    private String url;

    /**
     * 游记名称
     */
    private String noteName;

    /**
     * 游记内容
     */
    private String content;

    /**
     * 游记摘要
     */
    private String shortContent;

    /**
     * 游记封面图片链接
     */
    private String imageUrl;

    /**
     * 游记浏览总数
     */
    private Integer viewCount;

    /**
     * 游记评论总数
     */
    private Integer commentCount;

    /**
     * 游记顶的总数
     */
    private Integer upCount;

    /**
     * 游记收藏的总数
     */
    private Integer collectCount;

    /**
     * 游记分享总数
     */
    private Integer shareCount;

    /**
     * 游记字数总数
     */
    private Integer wordCount;

    /**
     * 游记图片总数
     */
    private Integer imageCount;

    /**
     * 游记帮助人总数
     */
    private Integer helpPeopleCount;

    /**
     * 游记地点
     */
    private String destination;

    /**
     * 旅行时间
     */
    private String travelTime;

    /**
     * 旅行天数
     */
    private Integer travelDay;

    /**
     * 旅行朋友
     */
    private String travelPeople;

    /**
     * 旅行花费
     */
    private Integer travelCost;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 爬取时间
     */
    private LocalDateTime crawlTime;

    /**
     * 创建时间
     */
    private LocalDateTime createDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
