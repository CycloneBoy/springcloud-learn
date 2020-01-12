package com.cycloneboy.springcloud.slmall.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author Exrickx
 */
@Data
public class ItemDto implements Serializable {

    private static final long serialVersionUID = 7405365465317537424L;

    private Long id;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String detail;

    private String image;

    private Long cid;

    private String cname;

    private Byte status;

    private Date created;

    private Date updated;

}
