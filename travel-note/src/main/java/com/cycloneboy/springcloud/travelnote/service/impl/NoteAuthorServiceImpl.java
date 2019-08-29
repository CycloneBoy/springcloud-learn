package com.cycloneboy.springcloud.travelnote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.travelnote.dao.NoteAuthorMapper;
import com.cycloneboy.springcloud.travelnote.entity.NoteAuthor;
import com.cycloneboy.springcloud.travelnote.service.NoteAuthorService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 马蜂窝作者 服务实现类
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Service
public class NoteAuthorServiceImpl extends ServiceImpl<NoteAuthorMapper, NoteAuthor> implements NoteAuthorService {

}
