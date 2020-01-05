package com.cycloneboy.springcloud.springlearn.springcommon.cop5.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * Create by  sl on 2020-01-05 08:36
 */
@Slf4j
public class LinuxCondition implements Condition {


  /**
   * Determine if the condition matches.
   *
   * @param context  the condition context
   * @param metadata metadata of the {@link AnnotationMetadata class} or {@link MethodMetadata
   *                 method} being checked
   * @return {@code true} if the condition matches and the component can be registered, or {@code
   * false} to veto the annotated component's registration
   */
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    // 获取IOC容器正在使用的beanFactory
    ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    Environment environment = context.getEnvironment();
    String osName = environment.getProperty("os.name");
    log.info("os-name: {}", osName);
    return osName.contains("Linux");
  }
}
