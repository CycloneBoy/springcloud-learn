package com.cycloneboy.springcloud.searchhouse.service;

import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseBucketDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.MapSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import java.util.List;

/**
 * 检索接口
 * <p>
 * Create by  sl on 2019-12-19 15:41
 */
public interface ISearchService {

  /**
   * 索引目标房源
   */
  void index(Long houseId);

  /**
   * 移除房源索引
   */
  void remove(Long houseId);

  /**
   * 查询房源接口
   */
  ServiceMultiResult<Long> query(RentSearch rentSearch);

  /**
   * 获取补全建议关键词
   */
  ServiceResult<List<String>> suggest(String prefix);

  /**
   * 聚合特定小区的房间数
   */
  ServiceResult<Long> aggregateDistrictHouse(String cityEnName, String regionEnName,
      String district);

  /**
   * 聚合城市数据
   */
  ServiceMultiResult<HouseBucketDTO> mapAggregate(String cityEnName);

  /**
   * 城市级别查询
   */
  ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy, String orderDirection,
      int start, int size);

  /**
   * 精确范围数据查询
   */
  ServiceMultiResult<Long> mapQuery(MapSearch mapSearch);


}
