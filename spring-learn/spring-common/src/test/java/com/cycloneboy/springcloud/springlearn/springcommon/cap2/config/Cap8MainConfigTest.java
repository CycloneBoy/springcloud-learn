package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap8.config.Cap8MainConfig;
import com.cycloneboy.springcloud.springlearn.springcommon.cap8.model.Bird;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap8MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap8MainConfig.class);
    log.info("IOC创建完成...........................");

    String[] names = app.getBeanDefinitionNames();
    for (String name : names) {
      log.info(name);
    }

    Bird bird = (Bird) app.getBean("bird");
    log.info(bird.toString());

    ConfigurableEnvironment environment = app.getEnvironment();
    String property = environment.getProperty("bird.color");
    log.info(property);

    app.close();


  }
}