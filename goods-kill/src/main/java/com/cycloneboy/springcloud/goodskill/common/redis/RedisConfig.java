package com.cycloneboy.springcloud.goodskill.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Create by  sl on 2019-12-13 16:29
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  /**
   * 缓存管理器
   *
   * @param redisTemplate
   * @return
   */
//  @Bean
//  public CacheManager cacheManager(RedisTemplate redisTemplate) {
//    return new RedisCacheManager(redisTemplate, RedisCacheConfiguration.defaultCacheConfig());
//  }

  /**
   * 自定义key(消息队列 暂时用不到 自行忽略) <br>
   * <br>此方法将会根据类名+方法名+所有参数的值生成唯一的一个key, <br>
   * 即使@Cacheable中的value属性一样，key也会不一样。
   *
   * @return
   */
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append(method.getName());

      for (Object param : params) {
        sb.append(param.toString());
      }
      return sb.toString();
    };
  }

  /**
   * 序列化Java对象
   *
   * @param con
   * @return
   */
//  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory con) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
    Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
    serializer.setObjectMapper(mapper);

    template.setValueSerializer(serializer);
    template.setKeySerializer(new StringRedisSerializer());
    template.afterPropertiesSet();
    return template;

  }
}
