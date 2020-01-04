package com.cycloneboy.springcloud.springlearn.springcommon.cap2.config;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 自定义注解
 * <p>
 * Create by  sl on 2020-01-04 21:39
 */
@Slf4j
public class SlTypeFilter implements TypeFilter {

  private ClassMetadata classMetadata;

  /**
   * Determine whether this filter matches for the class described by the given metadata.
   *
   * @param metadataReader        the metadata reader for the target class
   * @param metadataReaderFactory a factory for obtaining metadata readers for other classes (such
   *                              as superclasses and interfaces)
   * @return whether this filter matches
   * @throws IOException in case of I/O failure when reading metadata
   */
  @Override
  public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
      throws IOException {

    // 获取当前类注解的信息
    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
    // 获取当前正在扫描的类的信息
    classMetadata = metadataReader.getClassMetadata();

    // 获取当前类资源(类的信息)
    Resource resource = metadataReader.getResource();

    String className = classMetadata.getClassName();
    log.info("------------>" + className);
    if (className.contains("er")) {
      return true;
    }

    return false;
  }
}
