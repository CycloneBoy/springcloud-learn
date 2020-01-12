package com.cycloneboy.springcloud.slmall.module.portal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by CycloneBoy on 2017/7/16.
 */
@Slf4j
//@Configuration
//@EnableSwagger2
class Swagger2Configuration {


  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
//        .apis(RequestHandlerSelectors.basePackage("com.cycloneboy.springcloud.goodskill"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Spring Boot 高并发秒杀学习")
        .description("学习高并发秒杀例子， 我的Github: https://github.com/CycloneBoy")
        .termsOfServiceUrl("https://github.com/CycloneBoy")
        .contact(
            new Contact("CycloneBoy", "https://github.com/CycloneBoy", "xuanfeng1992@gmail.com"))
        .version("1.0")
        .build();
  }
}
