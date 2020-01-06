package com.cycloneboy.springcloud.springlearn.springcommon.cap12.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2020-01-06 08:51
 */
@Slf4j
@Component
public class SlBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {

    log.info("SlBeanFactoryPostProcessor...postProcessBeanDefinitionRegistry,bean的数量:{}",
        registry.getBeanDefinitionCount());
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    log.info("SlBeanFactoryPostProcessor...postProcessBeanFactory,bean的数量:{}",
        beanFactory.getBeanDefinitionCount());
  }
}
