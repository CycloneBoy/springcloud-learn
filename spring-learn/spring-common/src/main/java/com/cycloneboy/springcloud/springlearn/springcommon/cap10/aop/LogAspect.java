package com.cycloneboy.springcloud.springlearn.springcommon.cap10.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Create by  sl on 2020-01-05 14:10
 */
@Slf4j
@Aspect
public class LogAspect {

  @Pointcut("execution(public int com.cycloneboy.springcloud.springlearn.springcommon.cap10.model.Calculator.*(..))")
  public void pointCut() {

  }

  @Before("pointCut()")
  public void logStart() {
    log.info("除法运行开始....参数列表是:{}");
  }

  @After("pointCut()")
  public void logEnd() {
    log.info("除法运行结束....参数列表是:{}");
  }

  @AfterReturning("pointCut()")
  public void logReturn() {
    log.info("除法正常返回....运行结果是是:{}");
  }

  @AfterThrowing("pointCut()")
  public void logException() {
    log.info("除法运行异常信息是....异常信息是:{}");
  }

  @Around("pointCut()")
  public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("@Around 执行目标方法之前");
    Object result = proceedingJoinPoint.proceed();

    log.info("@Around 执行目标方法之后");

    return result;
  }
}
