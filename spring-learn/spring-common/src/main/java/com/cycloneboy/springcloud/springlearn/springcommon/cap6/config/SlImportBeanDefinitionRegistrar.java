package com.cycloneboy.springcloud.springlearn.springcommon.cap6.config;

import com.cycloneboy.springcloud.springlearn.springcommon.model.Pig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义导入bean
 * <p>
 * Create by  sl on 2020-01-05 09:08
 */
public class SlImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

  /**
   * Register bean definitions as necessary based on the given annotation metadata of the importing
   * {@code @Configuration} class.
   * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
   * registered here, due to lifecycle constraints related to {@code @Configuration} class
   * processing.
   *
   * @param importingClassMetadata annotation metadata of the importing class
   * @param registry               current bean definition registry
   */
  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
      BeanDefinitionRegistry registry) {
    boolean bean1 = registry
        .containsBeanDefinition("com.cycloneboy.springcloud.springlearn.springcommon.model.Dog");

    boolean bean2 = registry
        .containsBeanDefinition("com.cycloneboy.springcloud.springlearn.springcommon.model.Fish");

    // 如果Dog和Fish都存在与IOC容器,则创建Pig

    if (bean1 && bean2) {
      // 封装bean
      BeanDefinition beanDefinition = new RootBeanDefinition(Pig.class);
      registry.registerBeanDefinition("pig", beanDefinition);
    }
  }
}
