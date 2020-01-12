package com.cycloneboy.springcloud.slmall.module.portal.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudController;
import com.cycloneboy.springcloud.slmall.module.portal.entity.User;
import com.cycloneboy.springcloud.slmall.module.portal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "管理员接口")
@RequestMapping("/user")
public class UserController extends BaseXCloudController<User, Long> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return userService;
    }

    @GetMapping(value = "/getUserByUsername")
    @ApiOperation("通过用户名获取用户")
    public BaseResponse getUserByUsername(@RequestParam String username) {
        log.info("{}", username);
        User u = userService.findUserByUsername(username);
        log.info("{}", u.getUsername());
        return new BaseResponse(u);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
