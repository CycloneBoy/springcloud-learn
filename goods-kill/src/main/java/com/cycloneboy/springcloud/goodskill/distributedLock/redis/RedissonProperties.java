package com.cycloneboy.springcloud.goodskill.distributedLock.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by  sl on 2019-12-13 17:48
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

  private int timeout = 3000;

  private String address;

  private String password;

  private int connectionPoolSize = 64;

  private int connectionMinimumIdleSize = 10;

  private int slaveConnectionPoolSize = 250;

  private int masterConnectionPoolSize = 250;

  private String[] sentinelAddresses;

  private String masterName;

}
