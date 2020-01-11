package com.cycloneboy.springcloud.slmall.common.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * @author sl
 */
@Data
public class City implements Serializable {

  String country;

  String province;

  String city;
}
