package com.cycloneboy.springcloud.slmall.module.mmall.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by geely
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;

}
