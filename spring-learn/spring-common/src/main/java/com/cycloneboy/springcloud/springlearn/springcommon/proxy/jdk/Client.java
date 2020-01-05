package com.cycloneboy.springcloud.springlearn.springcommon.proxy.jdk;


import com.cycloneboy.springcloud.springlearn.springcommon.proxy.cglib.CGLibProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * JDK代理是不需要第三方库支持，只需要JDK环境就可以进行代理，使用条件:
 *
 * <p>1）实现InvocationHandler
 *
 * <p>2）使用Proxy.newProxyInstance产生代理对象
 *
 * <p>3）被代理的对象必须要实现接口
 *
 * <p>CGLib必须依赖于CGLib的类库，但是它需要类来实现任何接口代理的是指定的类生成一个子类，
 *
 * <p>覆盖其中的方法，是一种继承但是针对接口编程的环境下推荐使用JDK的代理；
 * <p>
 *
 * @See https://blog.csdn.net/yhl_jxy/article/details/80635012
 * <p>
 * Create by sl on 2020-01-05 21:13
 */
@Slf4j
public class Client {

  public static void main(String[] args) {
    log.info("---------------CGLibProxy-------------");

    CGLibProxy cgLibProxy = new CGLibProxy();
    IUserManager userManager = (IUserManager) cgLibProxy.createProxyObject(new UserManagerImpl());
    userManager.addUser("sl", "123");

    log.info("---------------JdkProxy-------------");
    JDKProxy jdkProxy = new JDKProxy();
    IUserManager userManagerJdk = (IUserManager) jdkProxy.newProxy(new UserManagerImpl());
    userManagerJdk.addUser("sl-jdk", "111");
  }
}
