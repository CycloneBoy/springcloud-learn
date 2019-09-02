package com.cycloneboy.springcloud.travelnote.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.common.entity.TravelNote;
import com.cycloneboy.springcloud.travelnote.dao.TravelNoteMapper;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteService;
import java.util.List;
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

  /**
   * 根据年月日查询游记
   *
   * @param year
   * @param month
   * @param day
   * @return
   */
  public List<TravelNote> getNoteList(int year, int month, int day) {
    QueryWrapper<TravelNote> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(TravelNote::getYear, year);

    if (month > 0) {
      queryWrapper.lambda().eq(TravelNote::getMonth, month);
    }

    if (day > 0) {
      queryWrapper.lambda().eq(TravelNote::getDay, day);
    }

    return this.list(queryWrapper);
  }

}
