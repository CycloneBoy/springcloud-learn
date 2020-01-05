package com.cycloneboy.springcloud.springlearn.springcommon.cap9.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 实现BeanNameAware Create by  sl on 2020-01-05 13:41
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class Light implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

  private ApplicationContext applicationContext;

  @Override
  public void setBeanName(String name) {
    log.info("当前bean的名字:{}", name);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    log.info("传入IOC容器:{}", applicationContext);
    this.applicationContext = applicationContext;
  }


  @Override
  public void setEmbeddedValueResolver(StringValueResolver resolver) {
    String resolveStringValue = resolver.resolveStringValue("你好 ${os.name},计算 #{12-2}");
    log.info(resolveStringValue);
  }
}
