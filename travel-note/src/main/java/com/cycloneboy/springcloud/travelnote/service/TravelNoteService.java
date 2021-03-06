package com.cycloneboy.springcloud.travelnote.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cycloneboy.springcloud.common.entity.TravelNote;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-02
 */
public interface TravelNoteService extends IService<TravelNote> {

  List<TravelNote> getNoteList(int year, int month, int day);

}
