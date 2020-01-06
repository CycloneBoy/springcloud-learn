package com.cycloneboy.springcloud.springlearn.springcommon.cap11.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Configuration
@ComponentScan(value = {"com.cycloneboy.springcloud.springlearn.springcommon.cap11"})
@EnableTransactionManagement
public class Cap11MainConfig {


  /**
   * 配置绑定
   *
   * @return
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DruidDataSource datasource() {

    DruidDataSource source = new DruidDataSource();
    // dataSource.setUrl();
    return source;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(datasource());
  }

}
