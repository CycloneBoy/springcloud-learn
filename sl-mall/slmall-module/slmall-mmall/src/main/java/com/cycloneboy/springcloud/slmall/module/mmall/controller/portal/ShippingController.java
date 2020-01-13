package com.cycloneboy.springcloud.slmall.module.mmall.controller.portal;

import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.Const;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ResponseCode;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Shipping;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.User;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IShippingService;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by geely
 */
@Api(description = "Shipping接口")
@RestController
@RequestMapping("/shipping/")
public class ShippingController {


    @Autowired
    private IShippingService iShippingService;


    @GetMapping("add.do")
    public ServerResponse add(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN);
        }
        return iShippingService.add(user.getId(), shipping);
    }


    @GetMapping("del.do")
    public ServerResponse del(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN);
        }
        return iShippingService.del(user.getId(), shippingId);
    }

    @GetMapping("update.do")
    public ServerResponse update(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN);
        }
        return iShippingService.update(user.getId(), shipping);
    }


    @GetMapping("select.do")
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN);
        }
        return iShippingService.select(user.getId(), shippingId);
    }


    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(
        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
        HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN);
        }
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }


}
