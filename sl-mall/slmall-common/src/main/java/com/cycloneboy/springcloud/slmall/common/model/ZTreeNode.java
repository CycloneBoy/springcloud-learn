package com.cycloneboy.springcloud.slmall.common.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Exrick
 * @date 2017/8/2
 */
@Data
public class ZTreeNode implements Serializable {

    private static final long serialVersionUID = -6723669454003631428L;

    private int id;

    private int pId;

    private String name;

    private Boolean isParent;

    private Boolean open;

    private String icon;

    private int status;

    private int sortOrder;

    private String remark;

    /**
     * 板块限制商品数量
     */
    private int limitNum;

    /**
     * 板块类型
     */
    private int type;


}
