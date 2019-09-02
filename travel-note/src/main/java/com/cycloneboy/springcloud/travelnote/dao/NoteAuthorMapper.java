package com.cycloneboy.springcloud.travelnote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cycloneboy.springcloud.common.entity.NoteAuthor;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 马蜂窝作者 Mapper 接口
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Mapper
public interface NoteAuthorMapper extends BaseMapper<NoteAuthor> {

}
