package com.cycloneboy.springcloud.travelnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.travelnote.dao.TravelNoteDetailMapper;
import com.cycloneboy.springcloud.travelnote.service.TravelNoteDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 马蜂窝作者 服务实现类
 * </p>
 *
 * @author cycloneboy
 * @since 2019-08-05
 */
@Slf4j
@Service
public class TravelNoteDetailServiceImpl extends ServiceImpl<TravelNoteDetailMapper, TravelNoteDetail> implements TravelNoteDetailService {

  /**
   * 保存游记
   *
   * @param travelNoteDetail
   */
  public void saveTravelNoteDetail(TravelNoteDetail travelNoteDetail) {

    TravelNoteDetail travelNoteDetailOld = this.getOne(
        new LambdaQueryWrapper<TravelNoteDetail>()
            .eq(TravelNoteDetail::getNoteId, travelNoteDetail.getNoteId()));

    if (travelNoteDetailOld != null) {
//            BeanUtils.copyProperties(travelNoteDetailOld, travelNoteDetail);
      travelNoteDetail.setId(travelNoteDetailOld.getId());
      travelNoteDetail.setAuthorId(travelNoteDetailOld.getAuthorId());
      travelNoteDetail.setShortContent(travelNoteDetailOld.getShortContent());
      travelNoteDetail.setImageUrl(travelNoteDetailOld.getImageUrl());
    }

    this.saveOrUpdate(travelNoteDetail);
    log.info("保存详细游记信息：" + travelNoteDetail.toString());
  }
}
