package com.cycloneboy.springcloud.travelnote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 代理
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_proxy")
public class Proxy extends Model<Proxy> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 代理类型: 0 - XiciDaili
     */
    private Integer type;

    /**
     * 代理ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 协议类型
     */
    private String protocol;

    /**
     * 速度
     */
    private Float speed;

    /**
     * 连接时间
     */
    private Float time;

    /**
     * 存活时间
     */
    @TableField("liveTime")
    private Integer liveTime;

    /**
     * 验证时间
     */
    @TableField("checkTime")
    private LocalDateTime checkTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
