package com.cycloneboy.springcloud.springlearn.springcommon.proxy.jdk;

/**
 * 用户管理接口(真实主题和代理主题的共同接口，这样在任何可以使用真实主题的地方都可以使用代理主题代理。
 * <p>
 * --被代理接口定义
 * <p>
 * Create by sl on 2020-01-05 20:53
 */
public interface IUserManager {

  void addUser(String id, String password);

}
