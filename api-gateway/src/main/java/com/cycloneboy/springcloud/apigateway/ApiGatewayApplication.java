package com.cycloneboy.springcloud.apigateway;

import com.cycloneboy.springcloud.apigateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  public AccessFilter accessFilter() {
    return new AccessFilter();
  }

//  @Bean
//  public PatternServiceRouteMapper serviceRouteMapper() {
//    return new PatternServiceRouteMapper(
//        "(?<name>^.+)-(?<version>v.+$",
//        "${version}/${name}");
//  }

  @RefreshScope
  @Bean
  public ZuulProperties zuulProperties() {
    return new ZuulProperties();
  }
}
