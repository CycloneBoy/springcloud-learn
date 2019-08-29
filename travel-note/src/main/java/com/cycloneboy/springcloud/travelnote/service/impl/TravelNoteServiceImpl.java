package com.cycloneboy.springcloud.travelnote.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.travelnote.dao.TravelNoteMapper;
import com.cycloneboy.springcloud.travelnote.entity.TravelNote;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
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
public class TravelNoteServiceImpl extends ServiceImpl<TravelNoteMapper, TravelNote> implements TravelNoteService {

}
