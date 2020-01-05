package com.cycloneboy.springcloud.springlearn.springcommon.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2020-01-05 21:50
 */
@Slf4j
public class HashMapProxyTest {

  public static void main(String[] args) {
    final HashMap<String, Object> hashMap = new HashMap<>();

    Map<String, Object> mapProxy = (Map<String, Object>) Proxy
        .newProxyInstance(HashMap.class.getClassLoader(), HashMap.class.getInterfaces(),
            new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(hashMap, args);
              }
            });
    mapProxy.put("key1", "value1");
    System.out.println(mapProxy);
  }


}
