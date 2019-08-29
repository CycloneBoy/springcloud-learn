package com.cycloneboy.springcloud.travelnote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
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
@TableName("t_note_author")
public class NoteAuthor extends Model<NoteAuthor> {

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
     * 游记作者首页链接
     */
    private String url;

    /**
     * 游记作者名称
     */
    private String name;

    /**
     * 游记作者图像链接
     */
    private String imageUrl;

    /**
     * 游记浏览总数
     */
    private Long travelViewCount;

    /**
     * 游记评论总数
     */
    private Long travelCommentCount;

    /**
     * 游记顶的总数
     */
    private Long travelUpCount;

    /**
     * 游记总数
     */
    private Integer travelTotal;

    /**
     * 作者等级
     */
    private String level;

    /**
     * 作者城市
     */
    private String city;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
