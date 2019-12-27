package com.cycloneboy.springcloud.searchhouse.controller;

import com.cycloneboy.springcloud.searchhouse.common.RentValueBlock;
import com.cycloneboy.springcloud.searchhouse.common.ServiceMultiResult;
import com.cycloneboy.springcloud.searchhouse.common.ServiceResult;
import com.cycloneboy.springcloud.searchhouse.entity.SupportAddress;
import com.cycloneboy.springcloud.searchhouse.model.dto.HouseDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.SupportAddressDTO;
import com.cycloneboy.springcloud.searchhouse.model.dto.UserDTO;
import com.cycloneboy.springcloud.searchhouse.model.form.RentSearch;
import com.cycloneboy.springcloud.searchhouse.service.IAddressService;
import com.cycloneboy.springcloud.searchhouse.service.IHouseService;
import com.cycloneboy.springcloud.searchhouse.service.ISearchService;
import com.cycloneboy.springcloud.searchhouse.service.IUserService;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Create by  sl on 2019-12-19 14:33
 */
@Slf4j
@Controller
public class HouseController {

  @Autowired
  private IHouseService houseService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IAddressService addressService;

  @Autowired
  private ISearchService searchService;

  /**
   * 获取租房列表
   *
   * @param rentSearch
   * @param model
   * @param session
   * @param redirectAttributes
   * @return
   */
  @GetMapping("rent/house")
  public String rentHouse(@ModelAttribute RentSearch rentSearch, Model model,
      HttpSession session, RedirectAttributes redirectAttributes) {
    if (rentSearch.getCityEnName() == null) {
      String cityEnNameInSession = (String) session.getAttribute("cityEnName");
      if (cityEnNameInSession == null) {
        redirectAttributes.addAttribute("msg", "must_chose_city");
        return "redirect:/index";
      } else {
        rentSearch.setCityEnName(cityEnNameInSession);
      }
    } else {
      session.setAttribute("cityEnName", rentSearch.getCityEnName());
    }

    ServiceResult<SupportAddressDTO> city = addressService.findCity(rentSearch.getCityEnName());
    if (!city.isSuccess()) {
      redirectAttributes.addAttribute("msg", "must_chose_city");
      return "redirect:/index";
    }
    model.addAttribute("currentCity", city.getResult());

    ServiceMultiResult addressResult = addressService
        .findAllRegionsByCityName(rentSearch.getCityEnName());

    if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
      redirectAttributes.addAttribute("msg", "must_chose_city");
      return "redirect:/index";
    }

    ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.query(rentSearch);

    model.addAttribute("total", serviceMultiResult.getTotal());
    model.addAttribute("houses", serviceMultiResult.getResult());

    if (rentSearch.getRegionEnName() == null) {
      rentSearch.setRegionEnName("*");
    }

    model.addAttribute("searchBody", rentSearch);
    model.addAttribute("regions", addressResult.getResult());

    model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
    model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);

    model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
    model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));

    return "rent-list";
  }

  /**
   * 显示详细的租房信息
   *
   * @param houseId
   * @param model
   * @return
   */
  @GetMapping("rent/house/show/{id}")
  public String show(@PathVariable(value = "id") Long houseId, Model model) {
    if (houseId <= 0) {
      return "404";
    }

    ServiceResult<HouseDTO> serviceResult = houseService.findCompleteOne(houseId);

    HouseDTO houseDTO = serviceResult.getResult();
    Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService
        .findCityAndRegion(houseDTO.getCityEnName(), houseDTO.getRegionEnName());

    SupportAddressDTO city = addressMap.get(SupportAddress.Level.CITY);
    SupportAddressDTO region = addressMap.get(SupportAddress.Level.REGION);

    model.addAttribute("city", city);
    model.addAttribute("region", region);

    ServiceResult<UserDTO> userDTOServiceResult = userService.findById(houseDTO.getAdminId());
    model.addAttribute("agent", userDTOServiceResult.getResult());
    model.addAttribute("house", houseDTO);

    ServiceResult<Long> aggResult = searchService
        .aggregateDistrictHouse(city.getEnName(), region.getEnName(), houseDTO.getDistrict());
    model.addAttribute("houseCountInDistrict", 0);

    return "house-detail";


  }
}
