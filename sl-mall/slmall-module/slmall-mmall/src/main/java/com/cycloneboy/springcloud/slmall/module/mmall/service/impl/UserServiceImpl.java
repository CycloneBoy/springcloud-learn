package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.UserDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.User;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 17:54
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private UserDao userDao;

  @Override
  public ServerResponse<User> login(String username, String password) {
    return null;
  }

  @Override
  public ServerResponse<String> register(User user) {
    return null;
  }

  @Override
  public ServerResponse<String> checkValid(String str, String type) {
    return null;
  }

  @Override
  public ServerResponse selectQuestion(String username) {
    return null;
  }

  @Override
  public ServerResponse<String> checkAnswer(String username, String question, String answer) {
    return null;
  }

  @Override
  public ServerResponse<String> forgetResetPassword(String username, String passwordNew,
      String forgetToken) {
    return null;
  }

  @Override
  public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
    return null;
  }

  @Override
  public ServerResponse<User> updateInformation(User user) {
    return null;
  }

  @Override
  public ServerResponse<User> getInformation(Integer userId) {
    return null;
  }

  @Override
  public ServerResponse checkAdminRole(User user) {
    return null;
  }

  @Override
  public BaseXCloudDao<User, Integer> getRepository() {
    return userDao;
  }
}
