package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.common.HouseSort;
import com.cycloneboy.springcloud.searchhouse.common.HouseStatus;
import com.cycloneboy.springcloud.searchhouse.common.HouseSubscribeStatus;
import com.cycloneboy.springcloud.searchhouse.common.LoginUserUtil;
import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.entity.House;
import com.cycloneboy.springcloud.searchhouse.entity.HouseDetail;
import com.cycloneboy.springcloud.searchhouse.entity.HousePicture;
import com.cycloneboy.springcloud.searchhouse.entity.HouseSubscribe;
import com.cycloneboy.springcloud.searchhouse.entity.HouseTag;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseDetailDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.HousePictureDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseSubscribeDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.DatatableSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.HouseForm;
import com.cycloneboy.springcloud.searchhouse.model.form.MapSearch;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import com.cycloneboy.springcloud.searchhouse.repository.HouseDetailRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HousePictureRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseRepository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseSubscribeRespository;
import com.cycloneboy.springcloud.searchhouse.repository.HouseTagRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayStationRepository;
import com.cycloneboy.springcloud.searchhouse.service.IHouseService;
import com.cycloneboy.springcloud.searchhouse.service.IQiNiuService;
import com.cycloneboy.springcloud.searchhouse.service.ISearchService;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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
  private HouseSubscribeRespository subscribeRespository;

  @Autowired
  private IQiNiuService qiNiuService;

  @Autowired
  private ISearchService searchService;


  @Value("${qiniu.cdn.prefix}")
  private String cdnPrefix;

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
    List<HouseDTO> houseDTOS = new ArrayList<>();
    Sort sort = new Sort(Direction.fromString(searchBody.getDirection()),
        searchBody.getOrderBy());
    int page = searchBody.getStart() / searchBody.getLength();

    PageRequest pageable = PageRequest.of(page, searchBody.getLength());

    Specification<House> specification = (root, query, cb) -> {
      Predicate predicate = cb.equal(root.get("adminId"), LoginUserUtil.getLoginUserId());
      predicate = cb
          .and(predicate, cb.notEqual(root.get("status"), HouseStatus.DELETED.getValue()));

      if (searchBody.getCity() != null) {
        predicate = cb.and(predicate, cb.equal(root.get("cityEnName"), searchBody.getCity()));
      }

      if (searchBody.getStatus() != null) {
        predicate = cb.and(predicate, cb.equal(root.get("status"), searchBody.getStatus()));
      }

      if (searchBody.getCreateTimeMin() != null) {
        predicate = cb.and(predicate,
            cb.greaterThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMin()));
      }

      if (searchBody.getCreateTimeMax() != null) {
        predicate = cb.and(predicate,
            cb.lessThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMax()));
      }

      if (searchBody.getTitle() != null) {
        predicate = cb
            .and(predicate, cb.like(root.get("title"), "%" + searchBody.getTitle() + "%"));
      }

      return predicate;
    };

    Page<House> houses = houseRepository.findAll(specification, pageable);

    houses.forEach(house -> {
      HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
      houseDTO.setCover(this.cdnPrefix + house.getCover());
      houseDTOS.add(houseDTO);
    });

    return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOS);

  }

  /**
   * 查询完整房源信息
   *
   * @param id
   */
  @Override
  public ServiceResult<HouseDTO> findCompleteOne(Long id) {
    House house = houseRepository.findById(id).orElse(null);
    if (house == null) {
      return ServiceResult.notFound();
    }

    HouseDetail houseDetail = houseDetailRepository.findByHouseId(id);
    List<HousePicture> pictures = housePictureRepository.findAllByHouseId(id);

    HouseDetailDTO detailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
    List<HousePictureDTO> pictureDTOS = new ArrayList<>();
    for (HousePicture picture : pictures) {
      HousePictureDTO pictureDTO = modelMapper.map(picture, HousePictureDTO.class);
      pictureDTOS.add(pictureDTO);
    }

    List<HouseTag> houseTags = houseTagRepository.findAllByHouseId(id);
    List<String> tagList = new ArrayList<>();
    for (HouseTag tag : houseTags) {
      tagList.add(tag.getName());
    }

    HouseDTO result = modelMapper.map(house, HouseDTO.class);
    result.setHouseDetail(detailDTO);
    result.setPictures(pictureDTOS);
    result.setTags(tagList);

    if (LoginUserUtil.getLoginUserId() > 0) { // 已登录用户
      HouseSubscribe subscribe = subscribeRespository
          .findByHouseIdAndUserId(house.getId(), LoginUserUtil.getLoginUserId());
      if (subscribe != null) {
        result.setSubscribeStatus(subscribe.getStatus());
      }
    }

    return ServiceResult.of(result);
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
    if (rentSearch.getKeywords() != null && !rentSearch.getKeywords().isEmpty()) {
      ServiceMultiResult<Long> serviceResult = searchService.query(rentSearch);
      if (serviceResult.getTotal() == 0) {
        return new ServiceMultiResult<>(0, new ArrayList<>());
      }
      return new ServiceMultiResult<>(0, new ArrayList<>());
//      return new ServiceMultiResult<>(serviceResult.getTotal(), wrapperHouseResult(serviceResult.getResult()));

    }

    return simpleQuery(rentSearch);


  }

  /**
   * 简单从数据库查询房屋
   *
   * @param rentSearch
   * @return
   */
  private ServiceMultiResult<HouseDTO> simpleQuery(RentSearch rentSearch) {

    Sort sort = HouseSort.generateSort(rentSearch.getOrderBy(), rentSearch.getOrderDirection());
    int page = rentSearch.getStart() / rentSearch.getSize();

    PageRequest pageable = PageRequest.of(page, rentSearch.getSize(), sort);

    Specification<House> specification = (root, criteriaQuery, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder
          .equal(root.get("status"), HouseStatus.PASSES.getValue());

      predicate = criteriaBuilder.and(predicate,
          criteriaBuilder.equal(root.get("cityEnName"), rentSearch.getCityEnName()));

      if (HouseSort.DISTANCE_TO_SUBWAY_KEY.equals(rentSearch.getOrderBy())) {
        predicate = criteriaBuilder
            .and(predicate, criteriaBuilder.gt(root.get(HouseSort.DISTANCE_TO_SUBWAY_KEY), -1));
      }
      return predicate;
    };

    Page<House> houses = houseRepository.findAll(specification, pageable);
    List<HouseDTO> houseDTOS = new ArrayList<>();

    List<Long> houseIds = new ArrayList<>();
    Map<Long, HouseDTO> idToHouseMap = Maps.newHashMap();
    houses.forEach(house -> {
      HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
      houseDTO.setCover(this.cdnPrefix + house.getCover());
      houseDTOS.add(houseDTO);

      houseIds.add(house.getId());
      idToHouseMap.put(house.getId(), houseDTO);
    });

    wrapperHouseList(houseIds, idToHouseMap);
    return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOS);

  }

  /**
   * 渲染详细信息 及 标签
   */
  private void wrapperHouseList(List<Long> houseIds, Map<Long, HouseDTO> idToHouseMap) {
    List<HouseDetail> details = houseDetailRepository.findAllByHouseIdIn(houseIds);

    details.forEach(houseDetail -> {
      HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
      HouseDetailDTO detail = modelMapper.map(houseDetail, HouseDetailDTO.class);
      houseDTO.setHouseDetail(detail);
    });

    List<HouseTag> houseTags = houseTagRepository.findAllByHouseIdIn(houseIds);
    houseTags.forEach(houseTag -> {
      HouseDTO houseDTO = idToHouseMap.get(houseTag.getHouseId());
      houseDTO.getTags().add(houseTag.getName());
    });

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
