package com.cycloneboy.springcloud.slmall.module.portal.service.impl;


import com.cycloneboy.springcloud.slmall.module.portal.dao.UserDao;
import com.cycloneboy.springcloud.slmall.module.portal.entity.User;
import com.cycloneboy.springcloud.slmall.module.portal.service.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Exrickx
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "user-service")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    @Override
    @Cacheable(key = "'user:' + #username")
    public User findUserByUsername(String username) {
        log.info("UserServiceImpl: {}", username);
        List<User> list = userDao.findUserByUsername(username);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
