package com.cycloneboy.springcloud.slmall.module.portal.dao;


import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.portal.entity.User;
import java.util.List;

/**
 * @author Exrickx
 */
public interface UserDao extends BaseXCloudDao<User, Long> {

  /**
   * 通过用户名获取用户
   *
   * @param username
   * @return
   */
  List<User> findUserByUsername(String username);
}
