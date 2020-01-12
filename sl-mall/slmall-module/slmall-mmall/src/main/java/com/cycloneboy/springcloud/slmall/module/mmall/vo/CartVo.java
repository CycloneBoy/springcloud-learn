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
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked;//是否已经都勾选

    private String imageHost;

}
