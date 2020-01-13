package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.User;

public interface UserDao extends BaseXCloudDao<User, Integer> {

  boolean existsUserByUsername(String username);

  boolean existsUserByEmail(String email);

  User findTopByUsernameAndPassword(String username, String password);

  User findTopByUsername(String username);

  User findByUsernameAndQuestionAndAnswer(String username, String question, String answer);

  User findByIdAndAndEmail(Integer id, String email);

}