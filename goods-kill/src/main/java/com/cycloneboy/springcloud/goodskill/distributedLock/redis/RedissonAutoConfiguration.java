package com.cycloneboy.springcloud.goodskill.distributedLock.redis;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by  sl on 2019-12-13 17:52
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

  @Autowired
  private RedissonProperties redissonProperties;

  @Bean
  @ConditionalOnProperty(name = "redisson.address")
  RedissonClient redissonSingle() {
    Config config = new Config();
    SingleServerConfig serverConfig = config.useSingleServer()
        .setAddress(redissonProperties.getAddress())
        .setTimeout(redissonProperties.getTimeout())
        .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
        .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

    if (StringUtils.isNoneBlank(redissonProperties.getPassword())) {
      serverConfig.setPassword(redissonProperties.getPassword());
    }

    return Redisson.create(config);
  }

  @Bean
  RedissLockUtil redissLockUtil(RedissonClient redissonClient) {
    RedissLockUtil redissLockUtil = new RedissLockUtil();
    redissLockUtil.setRedissonClient(redissonClient);
    return redissLockUtil;
  }

}
