package com.cycloneboy.springcloud.travelnote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.travelnote.dao.ProxyMapper;
import com.cycloneboy.springcloud.travelnote.entity.Proxy;
import com.cycloneboy.springcloud.travelnote.service.ProxyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代理 服务实现类
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-03
 */
@Service
public class ProxyServiceImpl extends ServiceImpl<ProxyMapper, Proxy> implements ProxyService {

}
