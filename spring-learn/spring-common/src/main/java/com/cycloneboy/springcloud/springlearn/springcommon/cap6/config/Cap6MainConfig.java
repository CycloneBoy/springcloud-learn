package com.cycloneboy.springcloud.springlearn.springcommon.cap6.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Dog;
import com.cycloneboy.springcloud.springlearn.springcommon.model.Order;
import com.cycloneboy.springcloud.springlearn.springcommon.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Create by  sl on 2020-01-04 21:20
 */
@Slf4j
@Import({Person.class, Dog.class, SlImportSelector.class, SlImportBeanDefinitionRegistrar.class})
@Configuration
public class Cap6MainConfig {


  /**
   * 1. @Bean: 导入第三方的类与包的组件,比如第三方的类,需要在我们的IOC容器中使用
   * <p>
   * 2. 包扫描和组件的注解(@controller,@service,@repository,@Componet),一般针对我们自己写的类
   * <p>
   * 3. @Import: 快速给容器导入一些组件,@Bean 比较简单
   * <p>
   * a. @Import 要导入的容器的组件, 容器会自动注册这个组件, Bean的id为全类名
   * <p>
   * b. ImportSelector: 接口,返回需要导入到容器的组件的全类名数组
   * <p>
   * c. ImportBeanDefinitionRegistrar 可以手动添加组件到IOC容器, 使用ImportBeanDefinitionRegistrar接口
   * <p>
   * 4. 使用spring的FactoryBean来创建
   */
  @Bean("test01")
  public Order order() {
    log.info("给容器中创建order: test01...........................");
    return new Order("1", "test01");
  }

  @Bean
  public SlFactoryBean slFactoryBean() {
    return new SlFactoryBean();
  }


}
