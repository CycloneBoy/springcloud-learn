package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import com.cycloneboy.springcloud.springlearn.springcommon.cap10.config.Cap10MainConfig;
import com.cycloneboy.springcloud.springlearn.springcommon.cap10.model.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by  sl on 2020-01-04 21:23
 */
@Slf4j
public class Cap10MainConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test01() {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(
        Cap10MainConfig.class);
    log.info("IOC创建完成...........................");

    Calculator calculator = app.getBean(Calculator.class);
    int result = calculator.div(4, 1);

    log.info("{}", result);

    app.close();


  }

}