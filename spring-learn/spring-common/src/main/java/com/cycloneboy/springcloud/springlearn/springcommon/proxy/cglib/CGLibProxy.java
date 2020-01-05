package com.cycloneboy.springcloud.springlearn.springcommon.proxy.cglib;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * cglib是针对类来实现代理的，它会对目标类产生一个代理子类，通过方法拦截技术对过滤父类的方法调用。
 * <p>
 * 代理子类需要实现MethodInterceptor接口
 * <p>
 * https://blog.csdn.net/fanrenxiang/article/details/81939357
 * <p>
 * Create by  sl on 2020-01-05 21:03
 */
@Slf4j
public class CGLibProxy implements MethodInterceptor {

  private Object targetObject;

  public Object createProxyObject(Object obj) {
    this.targetObject = obj;

    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(obj.getClass());
    enhancer.setCallback(this);
    enhancer.setUseCache(false);

    Object proxyObj = enhancer.create();
    // 返回代理对象
    return proxyObj;
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    Object ret = null;

    // 过滤方法
    if ("addUser".equals(method.getName())) {
      // 检查权限
      checkPermission();
    }

    ret = method.invoke(targetObject, objects);

    return ret;
  }

  public void checkPermission() {
    log.info("-----------------检查权限checkPermission-----------------");
  }
}
