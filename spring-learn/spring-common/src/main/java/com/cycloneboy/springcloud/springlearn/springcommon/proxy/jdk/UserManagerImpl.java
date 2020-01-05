package com.cycloneboy.springcloud.springlearn.springcommon.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2020-01-05 20:54
 */
@Slf4j
public class UserManagerImpl implements IUserManager {

  @Override
  public void addUser(String id, String password) {
    log.info("======调用了UserManagerImpl.addUser()方法======");
  }
}
