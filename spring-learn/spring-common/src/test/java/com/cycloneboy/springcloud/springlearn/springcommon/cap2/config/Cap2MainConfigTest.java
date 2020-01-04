package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap2MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap2MainConfig.class);

    String[] names = app.getBeanDefinitionNames();

    for (String name : names) {
      log.info(name);
    }
  }
}