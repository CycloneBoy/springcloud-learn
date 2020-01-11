package com.cycloneboy.springcloud.slmall.common.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * @author sl
 */
@Data
public class IpLocate implements Serializable {

  private String retCode;

  private City result;
}

