package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseBucketDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.MapSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import com.cycloneboy.springcloud.searchhouse.model.search.HouseIndexKey;
import com.cycloneboy.springcloud.searchhouse.model.search.HouseIndexMessage;
import com.cycloneboy.springcloud.searchhouse.model.search.HouseIndexTemplate;
import com.cycloneboy.springcloud.searchhouse.repository.HouseDetailRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseTagRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SupportAddressRepository;
import com.cycloneboy.springcloud.searchhouse.service.IAddressService;
import com.cycloneboy.springcloud.searchhouse.service.ISearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-19 16:50
 */
@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {

  private static final String INDEX_NAME = "xunwu";
  private static final String INDEX_TYPE = "house";
  private static final String INDEX_TOPIC = "house_build";

  @Autowired
  private HouseRepository houseRepository;

  @Autowired
  private HouseDetailRepository houseDetailRepository;

  @Autowired
  private HouseTagRepository tagRepository;

  @Autowired
  private SupportAddressRepository supportAddressRepository;

  @Autowired
  private IAddressService addressService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private TransportClient esClient;

  @Autowired
  private ObjectMapper objectMapper;

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


  /**
   * 创建索引
   *
   * @param indexTemplate
   * @return
   */
  private boolean create(HouseIndexTemplate indexTemplate) {
    if (updateSuggest(indexTemplate)) {
      return false;
    }

    try {
      IndexResponse response = this.esClient.prepareIndex(INDEX_NAME, INDEX_TYPE)
          .setSource(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON).get();
      log.debug("Create index with house: " + indexTemplate.getHouseId());

      return response.status() == RestStatus.CREATED;
    } catch (JsonProcessingException e) {
      log.error("Error to index house " + indexTemplate.getHouseId(), e);
      return false;
    }

  }

  private boolean updateSuggest(HouseIndexTemplate indexTemplate) {
    return false;
  }

  /**
   * 更新指定ID 的内容
   *
   * @param esId
   * @param indexTemplate
   * @return
   */
  private boolean update(String esId, HouseIndexTemplate indexTemplate) {
    if (updateSuggest(indexTemplate)) {
      return false;
    }

    try {
      UpdateResponse response = this.esClient.prepareUpdate(INDEX_NAME, INDEX_TYPE, esId)
          .setDoc(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON).get();
      log.debug("Update index with house: " + indexTemplate.getHouseId());

      return response.status() == RestStatus.OK;
    } catch (JsonProcessingException e) {
      log.error("Error to index house " + indexTemplate.getHouseId(), e);
      return false;
    }
  }

  /**
   * 删除和重建索引
   *
   * @param totalHit
   * @param indexTemplate
   * @return
   */
  private boolean deleteAndCreate(long totalHit, HouseIndexTemplate indexTemplate) {
    DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
        .newRequestBuilder(esClient)
        .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, indexTemplate.getHouseId()))
        .source(INDEX_NAME);

    log.debug("Delete by query for house: " + builder);

    BulkByScrollResponse response = builder.get();
    long deleted = response.getDeleted();
    if (deleted != totalHit) {
      log.warn("Need delete {}, but {} was deleted!", totalHit, deleted);
      return false;
    } else {
      return create(indexTemplate);
    }
  }

  private void remove(Long houseId, int retry) {
    if (retry > HouseIndexMessage.MAX_RETRY) {
      log.error("Retry remove times over 3 for house: " + houseId + " Please check it!");
      return;
    }

  }
}
