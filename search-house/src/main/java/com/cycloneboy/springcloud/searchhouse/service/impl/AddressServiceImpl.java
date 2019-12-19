package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.entity.Subway;
import com.cycloneboy.springcloud.searchhouse.entity.SubwayStation;
import com.cycloneboy.springcloud.searchhouse.entity.SupportAddress;
import com.cycloneboy.springcloud.searchhouse.entity.SupportAddress.Level;
import com.cycloneboy.springcloud.searchhouse.model.dto.SubwayDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.SubwayStationDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.SupportAddressDTO;
import com.cycloneboy.springcloud.searchhouse.model.search.BaiduMapLocation;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SubwayStationRepository;
import com.cycloneboy.springcloud.searchhouse.repository.SupportAddressRepository;
import com.cycloneboy.springcloud.searchhouse.service.IAddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-19 15:45
 */
@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {


  @Autowired
  private SupportAddressRepository supportAddressRepository;

  @Autowired
  private SubwayRepository subwayRepository;

  @Autowired
  private SubwayStationRepository subwayStationRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * 获取所有支持的城市列表
   */
  @Override
  public ServiceMultiResult<SupportAddressDTO> findAllCities() {
    List<SupportAddress> addressList = supportAddressRepository
        .findAllByLevel(Level.CITY.getValue());

    List<SupportAddressDTO> supportAddressDTOList = new ArrayList<>();
    for (SupportAddress supportAddress : addressList) {
      SupportAddressDTO addressDTO = modelMapper.map(supportAddress, SupportAddressDTO.class);
      supportAddressDTOList.add(addressDTO);
    }

    return new ServiceMultiResult<>(supportAddressDTOList);
  }

  /**
   * 根据英文简写获取具体区域的信息
   *
   * @param cityEnName
   * @param regionEnName
   */
  @Override
  public Map<Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
    Map<Level, SupportAddressDTO> result = new EnumMap<>(SupportAddress.Level.class);
    SupportAddress city = supportAddressRepository
        .findByEnNameAndLevel(cityEnName, Level.CITY.getValue());

    SupportAddress region = supportAddressRepository
        .findByEnNameAndBelongTo(regionEnName, city.getEnName());

    result.put(Level.CITY, modelMapper.map(city, SupportAddressDTO.class));
    result.put(Level.REGION, modelMapper.map(region, SupportAddressDTO.class));
    return result;
  }

  /**
   * 根据城市英文简写获取该城市所有支持的区域信息
   *
   * @param cityName
   */
  @Override
  public ServiceMultiResult findAllRegionsByCityName(String cityName) {
    if (cityName == null) {
      return new ServiceMultiResult<>(0, null);
    }

    List<SupportAddressDTO> result = new ArrayList<>();

    List<SupportAddress> regions = supportAddressRepository
        .findAllByLevelAndBelongTo(Level.REGION.getValue(), cityName);

    regions.forEach(region ->
        result.add(modelMapper.map(region, SupportAddressDTO.class))
    );

    return new ServiceMultiResult<>(result);
  }

  /**
   * 获取该城市所有的地铁线路
   *
   * @param cityEnName
   */
  @Override
  public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {
    List<SubwayDTO> result = new ArrayList<>();
    List<Subway> subways = subwayRepository.findAllByCityEnName(cityEnName);

    subways.forEach(subway -> result.add(modelMapper.map(subway, SubwayDTO.class)));

    return result;
  }

  /**
   * 获取地铁线路所有的站点
   *
   * @param subwayId
   */
  @Override
  public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
    List<SubwayStationDTO> result = new ArrayList<>();
    List<SubwayStation> stationList = subwayStationRepository.findAllBySubwayId(subwayId);

    stationList.forEach(
        subwayStation -> result.add(modelMapper.map(subwayStation, SubwayStationDTO.class)));
    return result;
  }

  /**
   * 获取地铁线信息
   *
   * @param subwayId
   */
  @Override
  public ServiceResult<SubwayDTO> findSubway(Long subwayId) {
    if (subwayId == null) {
      return ServiceResult.notFound();
    }

    Subway subway = subwayRepository.findById(subwayId).orElse(null);
    if (subway == null) {
      return ServiceResult.notFound();
    }

    return ServiceResult.of(modelMapper.map(subway, SubwayDTO.class));
  }

  /**
   * 获取地铁站点信息
   *
   * @param stationId
   */
  @Override
  public ServiceResult<SubwayStationDTO> findSubwayStation(Long stationId) {
    if (stationId == null) {
      return ServiceResult.notFound();
    }

    SubwayStation subwayStation = subwayStationRepository.findById(stationId).orElse(null);
    if (subwayStation == null) {
      return ServiceResult.notFound();
    }

    return ServiceResult.of(modelMapper.map(subwayStation, SubwayStationDTO.class));

  }

  /**
   * 根据城市英文简写获取城市详细信息
   *
   * @param cityEnName
   */
  @Override
  public ServiceResult<SupportAddressDTO> findCity(String cityEnName) {
    if (cityEnName == null) {
      return ServiceResult.notFound();
    }
    SupportAddress supportAddress = supportAddressRepository
        .findByEnNameAndLevel(cityEnName, Level.CITY.getValue());

    if (supportAddress == null) {
      return ServiceResult.notFound();
    }

    return ServiceResult.of(modelMapper.map(supportAddress, SupportAddressDTO.class));
  }

  /**
   * 根据城市以及具体地位获取百度地图的经纬度
   *
   * @param city
   * @param address
   */
  @Override
  public ServiceResult<BaiduMapLocation> getBaiduMapLocation(String city, String address) {
    return null;
  }

  /**
   * 上传百度LBS数据
   *
   * @param location
   * @param title
   * @param address
   * @param houseId
   * @param price
   * @param area
   */
  @Override
  public ServiceResult lbsUpload(BaiduMapLocation location, String title, String address,
      long houseId, int price, int area) {
    return null;
  }

  /**
   * 移除百度LBS数据
   *
   * @param houseId
   */
  @Override
  public ServiceResult removeLbs(Long houseId) {
    return null;
  }
}
