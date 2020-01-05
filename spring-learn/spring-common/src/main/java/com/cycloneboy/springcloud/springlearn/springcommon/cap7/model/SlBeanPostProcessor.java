package com.cycloneboy.springcloud.springlearn.springcommon.cap7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Bean 初始化之前和之后
 * <p>
 * Create by  sl on 2020-01-05 10:29
 */
@Slf4j
@AllArgsConstructor
@Data
@Component
public class SlBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    // 返回一个对象,传过来的对象
    // 在初始化方法调用之前进行后置处理工作
    // 构造函数 ->  postProcessBeforeInitialization -> init -> postProcessAfterInitialization -> 析构函数
    log.info("postProcessBeforeInitialization : {} - ", beanName);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    log.info("postProcessAfterInitialization : {} - ", beanName);
    return bean;
  }
}
