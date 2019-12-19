package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.common.HouseSubscribeStatus;
import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseSubscribeDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.DatatableSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.HouseForm;
import com.cycloneboy.springcloud.searchhouse.model.form.MapSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import com.cycloneboy.springcloud.searchhouse.repository.HouseDetailRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HousePictureRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseTagRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayStationRepository;
import com.cycloneboy.springcloud.searchhouse.service.IHouseService;
import com.cycloneboy.springcloud.searchhouse.service.IQiNiuService;
import com.cycloneboy.springcloud.searchhouse.service.ISearchService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-19 15:37
 */
@Slf4j
@Service
public class HouseServiceImpl implements IHouseService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HouseRepository houseRepository;

  @Autowired
  private HouseDetailRepository houseDetailRepository;

  @Autowired
  private HousePictureRepository housePictureRepository;

  @Autowired
  private HouseTagRepository houseTagRepository;

  @Autowired
  private SubwayRepository subwayRepository;

  @Autowired
  private SubwayStationRepository subwayStationRepository;

  @Autowired
  private IQiNiuService qiNiuService;

  @Autowired
  private ISearchService searchService;

  /**
   * 新增
   *
   * @param houseForm
   */
  @Override
  public ServiceResult<HouseDTO> save(HouseForm houseForm) {
    return null;
  }

  @Override
  public ServiceResult update(HouseForm houseForm) {
    return null;
  }

  @Override
  public ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody) {
    return null;
  }

  /**
   * 查询完整房源信息
   *
   * @param id
   */
  @Override
  public ServiceResult<HouseDTO> findCompleteOne(Long id) {
    return null;
  }

  /**
   * 移除图片
   *
   * @param id
   */
  @Override
  public ServiceResult removePhoto(Long id) {
    return null;
  }

  /**
   * 更新封面
   *
   * @param coverId
   * @param targetId
   */
  @Override
  public ServiceResult updateCover(Long coverId, Long targetId) {
    return null;
  }

  /**
   * 新增标签
   *
   * @param houseId
   * @param tag
   */
  @Override
  public ServiceResult addTag(Long houseId, String tag) {
    return null;
  }

  /**
   * 移除标签
   *
   * @param houseId
   * @param tag
   */
  @Override
  public ServiceResult removeTag(Long houseId, String tag) {
    return null;
  }

  /**
   * 更新房源状态
   *
   * @param id
   * @param status
   */
  @Override
  public ServiceResult updateStatus(Long id, int status) {
    return null;
  }

  /**
   * 查询房源信息集
   *
   * @param rentSearch
   */
  @Override
  public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {
    return null;
  }

  /**
   * 全地图查询
   *
   * @param mapSearch
   */
  @Override
  public ServiceMultiResult<HouseDTO> wholeMapQuery(MapSearch mapSearch) {
    return null;
  }

  /**
   * 精确范围数据查询
   *
   * @param mapSearch
   */
  @Override
  public ServiceMultiResult<HouseDTO> boundMapQuery(MapSearch mapSearch) {
    return null;
  }

  /**
   * 加入预约清单
   *
   * @param houseId
   */
  @Override
  public ServiceResult addSubscribeOrder(Long houseId) {
    return null;
  }

  /**
   * 获取对应状态的预约列表
   *
   * @param status
   * @param start
   * @param size
   */
  @Override
  public ServiceMultiResult<Pair<HouseDTO, HouseSubscribeDTO>> querySubscribeList(
      HouseSubscribeStatus status, int start, int size) {
    return null;
  }

  /**
   * 预约看房时间
   *
   * @param houseId
   * @param orderTime
   * @param telephone
   * @param desc
   */
  @Override
  public ServiceResult subscribe(Long houseId, Date orderTime, String telephone, String desc) {
    return null;
  }

  /**
   * 取消预约
   *
   * @param houseId
   */
  @Override
  public ServiceResult cancelSubscribe(Long houseId) {
    return null;
  }

  /**
   * 管理员查询预约信息接口
   *
   * @param start
   * @param size
   */
  @Override
  public ServiceMultiResult<Pair<HouseDTO, HouseSubscribeDTO>> findSubscribeList(int start,
      int size) {
    return null;
  }

  /**
   * 完成预约
   *
   * @param houseId
   */
  @Override
  public ServiceResult finishSubscribe(Long houseId) {
    return null;
  }
}
