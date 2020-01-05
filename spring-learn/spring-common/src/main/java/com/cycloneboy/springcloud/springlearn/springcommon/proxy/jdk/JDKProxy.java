package com.cycloneboy.springcloud.springlearn.springcommon.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;

/**
 * JDK动态代理类
 * <p>
 * Create by  sl on 2020-01-05 20:55
 */
@Slf4j
public class JDKProxy implements InvocationHandler {

  /**
   * 需要代理的目标对象
   */
  private Object targetObject;

  /**
   * 将目标对象传入进行代理
   *
   * @param targetObject
   * @return
   */
  public Object newProxy(Object targetObject) {
    this.targetObject = targetObject;

    //返回代理对象
    return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
        targetObject.getClass().getInterfaces(), this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 一般我们进行逻辑处理的函数比如这个地方是模拟检查权限
    checkPermission();

    // 设置方法的返回值
    Object ret = null;

    // 调用invoke方法，ret存储该方法的返回值
    ret = method.invoke(targetObject, args);
    return ret;
  }


  public void checkPermission() {
    log.info("-----------------检查权限checkPermission-----------------");
  }
}
