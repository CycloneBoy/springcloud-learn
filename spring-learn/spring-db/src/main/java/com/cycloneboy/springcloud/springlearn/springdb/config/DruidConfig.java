package com.cycloneboy.springcloud.springlearn.springdb.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by CycloneBoy on 2017/7/18.
 */
@Configuration
public class DruidConfig {

  /**
   * druid数据源状态监控
   *
   * @return
   */
  @Bean
  public ServletRegistrationBean statViewServlet() {
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
        new StatViewServlet(), "/druid/*");
    // IP 白名单
    servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    // IP黑名单
    servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
    // 控制台管理用户
    servletRegistrationBean.addInitParameter("loginUsername", "root");
    servletRegistrationBean.addInitParameter("loginPassword", "123456");
    servletRegistrationBean.addInitParameter("resetEnable", "false");
    return servletRegistrationBean;
  }

  /*
   * druid过滤器
   * /**
   * WebStatFilter用于采集web-jdbc关联监控的数据。
   * 属性filterName声明过滤器的名称,可选
   * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
   */
  @Bean
  public FilterRegistrationBean statFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
    // 添加过滤规则
    filterRegistrationBean.addUrlPatterns("/*");
    // 忽略过滤的格式
    filterRegistrationBean
        .addInitParameter("exclusions", "*.js,*.gif,*.png,*.jpg,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }

  @Bean
  public DruidStatInterceptor druidStatInterceptor() {
    DruidStatInterceptor dsInterceptor;
    dsInterceptor = new DruidStatInterceptor();
    return dsInterceptor;
  }

  @Bean
  @Scope("prototype")
  public JdkRegexpMethodPointcut druidStatPointcut() {
    JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
    pointcut.setPattern("com.cycloneboy.springcloud.springlearn.springdb.*");
    return pointcut;
  }

  @Bean
  public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor,
      JdkRegexpMethodPointcut druidStatPointcut) {
    DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
    defaultPointAdvisor.setPointcut(druidStatPointcut);
    defaultPointAdvisor.setAdvice(druidStatInterceptor);
    return defaultPointAdvisor;
  }
}
