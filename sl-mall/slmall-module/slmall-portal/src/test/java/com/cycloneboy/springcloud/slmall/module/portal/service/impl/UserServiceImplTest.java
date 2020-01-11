package com.cycloneboy.springcloud.slmall.module.portal.service.impl;

import com.cycloneboy.springcloud.slmall.module.portal.SlmallPortalApplicationTests;
import com.cycloneboy.springcloud.slmall.module.portal.entity.User;
import com.cycloneboy.springcloud.slmall.module.portal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by  sl on 2020-01-11 21:32
 */
@Slf4j
public class UserServiceImplTest extends SlmallPortalApplicationTests {

  @Autowired
  private UserService userService;

  @Test
  public void findUserByUsername() {
    log.info("userService:{}", userService);
    log.info("userDao:{}", userService.getRepository());
    User user = userService.findUserByUsername("admin");
    log.info("user:{}", user.getUsername());

  }
}