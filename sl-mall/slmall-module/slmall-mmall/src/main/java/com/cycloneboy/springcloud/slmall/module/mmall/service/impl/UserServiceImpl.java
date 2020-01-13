package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.Const;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.common.TokenCache;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.UserDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.User;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IUserService;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.MD5Util;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    boolean result = userDao.existsUserByUsername(username);
    if (!result) {
      return ServerResponse.createByErrorMessage("用户名/密码错误");
    }

    String md5Password = MD5Util.MD5EncodeUtf8(password);
    User user = userDao.findTopByUsernameAndPassword(username, md5Password);
    if (user == null) {
      return ServerResponse.createByErrorMessage("用户名/密码错误");
    }

    user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
    return ServerResponse.createBySuccess("登录成功", user);
  }

  @Override
  public ServerResponse<String> register(User user) {
    ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
    if (!validResponse.isSuccess()) {
      return validResponse;
    }
    validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
    if (!validResponse.isSuccess()) {
      return validResponse;
    }
    user.setRole(Const.Role.ROLE_CUSTOMER);
    //MD5加密
    user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
    user.setCreateTime(new Date());
    user.setUpdateTime(user.getCreateTime());
    User registerUser = userDao.save(user);
    if (registerUser == null) {
      return ServerResponse.createByErrorMessage("注册失败");
    }
    return ServerResponse.createBySuccessMessage("注册成功");
  }

  @Override
  public ServerResponse<String> checkValid(String str, String type) {
    if (StringUtils.isNotBlank(type)) {
      //开始校验
      if (Const.USERNAME.equals(type)) {
        boolean resultCount = userDao.existsUserByUsername(str);
        if (resultCount) {
          return ServerResponse.createByErrorMessage("用户名已存在");
        }
      }
      if (Const.EMAIL.equals(type)) {
        boolean resultCount = userDao.existsUserByEmail(str);
        if (resultCount) {
          return ServerResponse.createByErrorMessage("email已存在");
        }
      }
    } else {
      return ServerResponse.createByErrorMessage("参数错误");
    }
    return ServerResponse.createBySuccessMessage("校验成功");

  }

  @Override
  public ServerResponse selectQuestion(String username) {
    ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
    if (validResponse.isSuccess()) {
      //用户不存在
      return ServerResponse.createByErrorMessage("用户不存在");
    }
    User user = userDao.findTopByUsername(username);
    if (user != null && StringUtils.isNotBlank(user.getQuestion())) {
      return ServerResponse.createBySuccess(user.getQuestion());
    }
    return ServerResponse.createByErrorMessage("找回密码的问题是空的");
  }

  @Override
  public ServerResponse<String> checkAnswer(String username, String question, String answer) {
    User user = userDao.findByUsernameAndQuestionAndAnswer(username, question, answer);
    if (user != null) {
      //说明问题及问题答案是这个用户的,并且是正确的
      String forgetToken = UUID.randomUUID().toString();
      TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
      return ServerResponse.createBySuccess(forgetToken);
    }
    return ServerResponse.createByErrorMessage("问题的答案错误");
  }

  @Override
  public ServerResponse<String> forgetResetPassword(String username, String passwordNew,
      String forgetToken) {
    if (StringUtils.isBlank(forgetToken)) {
      return ServerResponse.createByErrorMessage("参数错误,token需要传递");
    }
    ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
    if (validResponse.isSuccess()) {
      //用户不存在
      return ServerResponse.createByErrorMessage("用户不存在");
    }
    String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
    if (StringUtils.isBlank(token)) {
      return ServerResponse.createByErrorMessage("token无效或者过期");
    }

    if (StringUtils.equals(forgetToken, token)) {
      String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);

      User existUser = userDao.findTopByUsername(username);
      existUser.setPassword(md5Password);
      userDao.saveAndFlush(existUser);

      return ServerResponse.createBySuccessMessage("修改密码成功");
    }

    return ServerResponse.createByErrorMessage("修改密码失败");
  }

  @Override
  public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
    User existUser = userDao
        .findTopByUsernameAndPassword(user.getUsername(), user.getPassword());
    if (existUser.getId().equals(user.getId())) {
      return ServerResponse.createByErrorMessage("旧密码错误");
    }

    user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
    existUser = userDao.saveAndFlush(user);

    if (!existUser.getPassword().equals(user.getPassword())) {
      return ServerResponse.createBySuccessMessage("密码更新成功");
    }
    return ServerResponse.createByErrorMessage("密码更新失败");
  }

  @Override
  public ServerResponse<User> updateInformation(User user) {
    //username是不能被更新的
    //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
    User updateUser = userDao.findByIdAndAndEmail(user.getId(), user.getEmail());
    if (updateUser != null) {
      return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
    }
    updateUser = get(user.getId()).orElse(null);
    if (updateUser == null) {
      return ServerResponse.createByErrorMessage("更新出错");
    }

    updateUser.setEmail(user.getEmail());
    updateUser.setPhone(user.getPhone());
    updateUser.setQuestion(user.getQuestion());
    updateUser.setAnswer(user.getAnswer());
    updateUser.setUpdateTime(new Date());

    User resultUser = userDao.saveAndFlush(updateUser);

    if (resultUser != null) {
      return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
    }
    return ServerResponse.createByErrorMessage("更新个人信息失败");
  }

  @Override
  public ServerResponse<User> getInformation(Integer userId) {
    User user = get(userId).orElse(null);
    if (user == null) {
      return ServerResponse.createByErrorMessage("找不到当前用户");
    }
    user.setPassword(null);

    return ServerResponse.createBySuccess(user);
  }

  @Override
  public ServerResponse checkAdminRole(User user) {
    if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
      return ServerResponse.createBySuccess();
    }
    return ServerResponse.createByError();
  }

  @Override
  public BaseXCloudDao<User, Integer> getRepository() {
    return userDao;
  }
}
