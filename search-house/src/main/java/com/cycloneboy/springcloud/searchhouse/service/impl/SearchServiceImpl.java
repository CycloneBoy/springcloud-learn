package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseBucketDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.MapSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import com.cycloneboy.springcloud.searchhouse.service.ISearchService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-19 16:50
 */
@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {

  /**
   * 索引目标房源
   *
   * @param houseId
   */
  @Override
  public void index(Long houseId) {

  }

  /**
   * 移除房源索引
   *
   * @param houseId
   */
  @Override
  public void remove(Long houseId) {

  }

  /**
   * 查询房源接口
   *
   * @param rentSearch
   */
  @Override
  public ServiceMultiResult<Long> query(RentSearch rentSearch) {
    return null;
  }

  /**
   * 获取补全建议关键词
   *
   * @param prefix
   */
  @Override
  public ServiceResult<List<String>> suggest(String prefix) {
    return null;
  }

  /**
   * 聚合特定小区的房间数
   *
   * @param cityEnName
   * @param regionEnName
   * @param district
   */
  @Override
  public ServiceResult<Long> aggregateDistrictHouse(String cityEnName, String regionEnName,
      String district) {
    return null;
  }

  /**
   * 聚合城市数据
   *
   * @param cityEnName
   */
  @Override
  public ServiceMultiResult<HouseBucketDTO> mapAggregate(String cityEnName) {
    return null;
  }

  /**
   * 城市级别查询
   *
   * @param cityEnName
   * @param orderBy
   * @param orderDirection
   * @param start
   * @param size
   */
  @Override
  public ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy, String orderDirection,
      int start, int size) {
    return null;
  }

  /**
   * 精确范围数据查询
   *
   * @param mapSearch
   */
  @Override
  public ServiceMultiResult<Long> mapQuery(MapSearch mapSearch) {
    return null;
  }
}
