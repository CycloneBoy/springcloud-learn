package com.cycloneboy.springcloud.dataprocess.utils;

import org.springframework.context.ApplicationContext;

/**
 * Create by  sl on 2019-11-30 17:22
 */
public class SpringUtils {

  private static ApplicationContext applicationContext;

  //获取上下文
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  //设置上下文
  public static void setApplicationContext(ApplicationContext applicationContext) {
    SpringUtils.applicationContext = applicationContext;
  }

  //通过名字获取上下文中的bean
  public static Object getBean(String name) {
    return applicationContext.getBean(name);
  }

  //通过类型获取上下文中的bean
  public static Object getBean(Class<?> requiredType) {
    return applicationContext.getBean(requiredType);
  }

}
