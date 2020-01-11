package com.cycloneboy.springcloud.slmall.module.portal.dao;

import com.cycloneboy.springcloud.slmall.module.portal.entity.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by  sl on 2020-01-11 21:14
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void findUserByUsername() {
    List<User> user = userDao.findUserByUsername("admin");
    log.info("user size:{}", user.size());

    User one = userDao.findById(1L).get();
    log.info("{}", one.getUsername());
  }
}