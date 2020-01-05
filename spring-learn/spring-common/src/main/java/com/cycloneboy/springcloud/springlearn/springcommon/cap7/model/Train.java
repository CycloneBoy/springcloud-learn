package com.cycloneboy.springcloud.springlearn.springcommon.cap7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Create by  sl on 2020-01-05 10:15
 */
@Slf4j
@AllArgsConstructor
@Data
@Component
public class Train implements InitializingBean, DisposableBean {

  private String name;

  public Train() {
    log.info("Train constructor...");
  }

  /**
   * Invoked by the containing {@code BeanFactory} on destruction of a bean.
   *
   * @throws Exception in case of shutdown errors. Exceptions will get logged but not rethrown to
   *                   allow other beans to release their resources as well.
   */
  @Override
  public void destroy() throws Exception {
    log.info("Train destroy...");
  }

  /**
   * Invoked by the containing {@code BeanFactory} after it has set all bean properties and
   * satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
   * <p>This method allows the bean instance to perform validation of its overall
   * configuration and final initialization when all bean properties have been set.
   *
   * @throws Exception in the event of misconfiguration (such as failure to set an essential
   *                   property) or if initialization fails for any other reason
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Train afterPropertiesSet...");
  }
}
