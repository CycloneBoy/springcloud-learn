package com.cycloneboy.springcloud.springlearn.springcommon.cap8.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Create by  sl on 2020-01-05 09:04
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bird {

  //   @Value 可以赋值基本数据,spring-el 表达式,properties
  @Value("sl")
  private String name;

  @Value("#{20-2}")
  private Integer age;

  @Value("${bird.color}")
  private String color;

}
