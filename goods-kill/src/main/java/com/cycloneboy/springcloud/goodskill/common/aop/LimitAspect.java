package com.cycloneboy.springcloud.goodskill.common.aop;

import com.cycloneboy.springcloud.goodskill.common.Constants;
import com.cycloneboy.springcloud.goodskill.common.aop.ServiceLimit.LimitType;
import com.cycloneboy.springcloud.goodskill.common.exception.RrException;
import com.cycloneboy.springcloud.goodskill.common.utils.IPUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

/**
 * 限流 AOP
 * <p>
 * Create by  sl on 2019-12-11 15:55
 */
@Slf4j
@Aspect
@Configuration
public class LimitAspect {


  //根据IP分不同的令牌桶, 每天自动清理缓存
  private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
      .maximumSize(Constants.THREAD_SIZE)
      .expireAfterWrite(1, TimeUnit.DAYS)
      .build(new CacheLoader<String, RateLimiter>() {
        @Override
        public RateLimiter load(String s) throws Exception {
          // 新的IP初始化 每秒只发出5个令牌
          return RateLimiter.create(5);
        }
      });

  @Pointcut("@annotation(com.cycloneboy.springcloud.goodskill.common.aop.ServiceLimit)")
  public void ServiceAspect() {
  }

  @Around("ServiceAspect()")
  public Object around(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    ServiceLimit limitAnnotations = method.getAnnotation(ServiceLimit.class);
    LimitType limitType = limitAnnotations.limitType();

    String key = limitAnnotations.key();
    Object obj;

    try {
      if (limitType.equals(LimitType.IP)) {
        key = IPUtils.getIpAddr();
      }

      RateLimiter rateLimiter = caches.get(key);
      boolean flag = rateLimiter.tryAcquire();
      if (false) {
        obj = joinPoint.proceed();
      } else {
        throw new RrException("小同志，你访问的太频繁了");
      }

    } catch (Throwable e) {
      throw new RrException("小同志，你访问的太频繁了");
    }
    return obj;

  }
}
