package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.SearchHouseApplicationTests;
import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.entity.SupportAddress.Level;
import com.cycloneboy.springcloud.searchhouse.model.dto.SubwayDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.SubwayStationDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.SupportAddressDTO;
import com.cycloneboy.springcloud.searchhouse.service.IAddressService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by  sl on 2019-12-19 16:33
 */
@Slf4j
public class AddressServiceImplTest extends SearchHouseApplicationTests {

  @Autowired
  private IAddressService addressService;

  @Test
  public void findAllCities() {
    ServiceMultiResult<SupportAddressDTO> allCities = addressService.findAllCities();
    log.info(allCities.toString());
  }

  @Test
  public void findCityAndRegion() {
    Map<Level, SupportAddressDTO> cityAndRegions = addressService.findCityAndRegion("bj", "hdq");
    cityAndRegions.forEach((cityAndRegion, addressDTO) -> {
      log.info(cityAndRegion.getValue());
      log.info(addressDTO.toString());
    });
  }

  @Test
  public void findAllRegionsByCityName() {
    ServiceMultiResult result = addressService.findAllRegionsByCityName("bj");
    log.info(result.toString());
  }

  @Test
  public void findAllSubwayByCity() {
    List<SubwayDTO> result = addressService.findAllSubwayByCity("bj");
    log.info(result.toString());
  }

  @Test
  public void findAllStationBySubway() {
    List<SubwayStationDTO> allStationBySubway = addressService.findAllStationBySubway(4L);
    log.info(allStationBySubway.toString());
  }

  @Test
  public void findSubway() {
    ServiceResult<SubwayDTO> subway = addressService.findSubway(1L);
    log.info(subway.toString());
  }

  @Test
  public void findSubwayStation() {
    ServiceResult<SubwayStationDTO> subwayStation = addressService.findSubwayStation(48L);
    log.info(subwayStation.toString());
  }

  @Test
  public void findCity() {
    ServiceResult<SupportAddressDTO> result = addressService.findCity("bj");
    log.info(result.toString());
  }

  @Test
  public void getBaiduMapLocation() {
  }

  @Test
  public void lbsUpload() {
  }

  @Test
  public void removeLbs() {
  }
}