package com.cycloneboy.springcloud.springlearn.springcommon.cap6.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Monkey;
import org.springframework.beans.factory.FactoryBean;

/**
 * Create by  sl on 2020-01-05 09:19
 */
public class SlFactoryBean implements FactoryBean<Monkey> {


  @Override
  public Monkey getObject() throws Exception {
    return new Monkey();
  }


  @Override
  public Class<?> getObjectType() {
    return Monkey.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
