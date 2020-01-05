package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap6.config.Cap6MainConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap6MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap6MainConfig.class);
    log.info("IOC创建完成...........................");

    Object bean1 = app.getBean("slFactoryBean");
    log.info("bean1的类型:{}", bean1.getClass());

    Object bean2 = app.getBean("slFactoryBean");
    log.info("bean2的类型:{}", bean1.getClass());

    log.info("{}", bean1 == bean2);

    String[] names = app.getBeanDefinitionNames();
    for (String name : names) {
      log.info(name);
    }


  }
}