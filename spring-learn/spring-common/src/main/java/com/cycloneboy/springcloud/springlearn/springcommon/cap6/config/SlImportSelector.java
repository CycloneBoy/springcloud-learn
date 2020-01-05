package com.cycloneboy.springcloud.springlearn.springcommon.cap6.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 导入自定义的bean的全类名
 * <p>
 * Create by  sl on 2020-01-05 09:01
 */
public class SlImportSelector implements ImportSelector {

  /**
   * Select and return the names of which class(es) should be imported based on the {@link
   * AnnotationMetadata} of the importing @{@link Configuration} class.
   *
   * @param importingClassMetadata
   */
  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[]{
        "com.cycloneboy.springcloud.springlearn.springcommon.model.Fish",
        "com.cycloneboy.springcloud.springlearn.springcommon.model.Cat",
        "com.cycloneboy.springcloud.springlearn.springcommon.model.Tiger"
    };
  }
}
