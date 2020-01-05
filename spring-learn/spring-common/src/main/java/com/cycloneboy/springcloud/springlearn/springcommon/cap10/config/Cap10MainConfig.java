package com.cycloneboy.springcloud.springlearn.springcommon.cap10.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap10.aop.LogAspect;
import com.cycloneboy.springcloud.springlearn.springcommon.cap10.model.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志切面类的方法需要动态的感知div()方法运行
 * <p>
 * 通知方法: 前置通知: logStart()  @Before
 * <p>
 * 后置通知: logEnd()  @After
 * <p>
 * 返回通知: logReturn() @AfterReturning
 * <p>
 * 遗产通知: logException() @AfterThrowing
 * <p>
 * 环绕通知: 返回正常通知 动态代理,需要手动执行joinPoint.procced,  @Around
 * <p>
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
//@ComponentScan({"com.cycloneboy.springcloud.springlearn.springcommon.cap10"})
public class Cap10MainConfig {

  @Bean
  public Calculator calculator() {
    return new Calculator();
  }

  @Bean
  public LogAspect logAspect() {
    return new LogAspect();
  }

}
