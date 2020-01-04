package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap3.config.Cap3MainConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap3MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap3MainConfig.class);

    String[] names = app.getBeanDefinitionNames();

    for (String name : names) {
      log.info(name);
    }

    Object bean1 = app.getBean("order");
    Object bean2 = app.getBean("order");

    log.info(String.valueOf(bean1 == bean2));
    log.info(bean1.toString());
    log.info(bean2.toString());
  }
}