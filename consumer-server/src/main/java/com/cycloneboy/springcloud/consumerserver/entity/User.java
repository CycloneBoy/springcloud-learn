package com.cycloneboy.springcloud.consumerserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: springclouddemo
 * @description:
 * @author : cycloneboy
 * @date:2018-12-21 00:05
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long id;

    private String username;

    private String name;

    private Integer age;

    private BigDecimal balance;
}
