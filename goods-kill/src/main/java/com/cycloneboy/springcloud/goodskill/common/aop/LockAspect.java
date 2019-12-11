package com.cycloneboy.springcloud.goodskill.common.aop;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 同步锁 AOP
 * <p>
 * Create by  sl on 2019-12-11 15:55
 */
@Slf4j
@Aspect
@Component
@Scope
//order越小越是最先执行，但更重要的是最先执行的最后结束。order默认值是2147483647
@Order(1)
public class LockAspect {

  /**
   * 思考：为什么不用synchronized service 默认是单例的，并发下lock只有一个实例
   */
  //互斥锁 参数默认false，不公平锁
  private static Lock lock = new ReentrantLock(true);

  //Service层切点     用于记录错误日志
  @Pointcut("@annotation(com.cycloneboy.springcloud.goodskill.common.aop.Servicelock)")
  public void lockAspect() {
  }

  @Around("lockAspect()")
  public Object around(ProceedingJoinPoint joinPoint) {
    lock.lock();
    Object obj;

    try {
      obj = joinPoint.proceed();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      throw new RuntimeException();
    } finally {
      lock.unlock();
    }
    return obj;
  }
}
