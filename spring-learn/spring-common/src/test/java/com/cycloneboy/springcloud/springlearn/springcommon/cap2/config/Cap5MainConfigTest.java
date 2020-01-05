package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cop5.config.Cap5MainConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap5MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap5MainConfig.class);
    log.info("IOC创建完成...........................");


  }
}