package com.cycloneboy.springcloud.travelnote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.common.entity.TravelImage;
import com.cycloneboy.springcloud.travelnote.dao.TravelImageMapper;
import com.cycloneboy.springcloud.travelnote.service.TravelImageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
@Service
public class TravelImageServiceImpl extends ServiceImpl<TravelImageMapper, TravelImage> implements TravelImageService {

}
