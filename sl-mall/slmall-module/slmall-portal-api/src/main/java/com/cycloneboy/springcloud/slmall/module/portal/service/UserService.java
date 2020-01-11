package com.cycloneboy.springcloud.slmall.module.portal.service;


import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.module.portal.entity.User;

/**
 * @author Exrickx
 */
public interface UserService extends BaseXCloudService<User, Long> {

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
