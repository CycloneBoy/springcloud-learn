package com.cycloneboy.springcloud.travelnote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cycloneboy.springcloud.common.entity.TravelNote;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
@Mapper
public interface TravelNoteMapper extends BaseMapper<TravelNote> {

}
