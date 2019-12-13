package com.cycloneboy.springcloud.goodskill.common.redis;

import com.cycloneboy.springcloud.goodskill.common.Constants;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 缓存工具类
 * <p>
 * Create by  sl on 2019-12-13 16:29
 */
@Slf4j
@Component
public class RedisUtil {

  @Resource
  private RedisTemplate<Serializable, Serializable> redisTemplate;

  /**
   * 缓存value操作
   *
   * @param key
   * @param value
   * @param time
   * @return
   */
  public boolean cacheValue(String key, Serializable value, long time) {
    String keyStr = Constants.KEY_PREFIX_VALUE + key;

    ValueOperations<Serializable, Serializable> valueOps = redisTemplate
        .opsForValue();
    valueOps.set(keyStr, value);
    if (time > 0) {
      redisTemplate.expire(keyStr, time, TimeUnit.SECONDS);
    }
    return true;

  }

  /**
   * 缓存value操作
   *
   * @param key
   * @param value
   * @param time
   * @param unit
   * @return
   */
  public boolean cacheValue(String key, Serializable value, long time, TimeUnit unit) {
    String keyStr = Constants.KEY_PREFIX_VALUE + key;

    ValueOperations<Serializable, Serializable> valueOps = redisTemplate
        .opsForValue();
    valueOps.set(keyStr, value);
    if (time > 0) {
      redisTemplate.expire(keyStr, time, unit);
    }
    return true;
  }

  /**
   * 缓存value操作
   *
   * @param key
   * @param value
   * @return
   */
  public boolean cacheValue(String key, Serializable value) {
    return cacheValue(key, value);
  }

  /**
   * 获取缓存
   *
   * @param key
   * @return
   */
  public Serializable getValue(String key) {
    ValueOperations<Serializable, Serializable> valueOps = redisTemplate
        .opsForValue();
    return valueOps.get(Constants.KEY_PREFIX_VALUE + key);
  }

  /**
   * 移除缓存
   *
   * @param key
   * @return
   */
  public boolean removeValue(String key) {
    return redisTemplate.delete(key);
  }
}
