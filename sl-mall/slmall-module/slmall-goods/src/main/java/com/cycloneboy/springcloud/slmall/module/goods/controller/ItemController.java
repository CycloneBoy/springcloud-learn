package com.cycloneboy.springcloud.slmall.module.goods.controller;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudController;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.common.constant.CommonConstant;
import com.cycloneboy.springcloud.slmall.module.goods.entity.Item;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2020-01-12 11:30
 */
@Slf4j
@RestController
@Api(description = "商品接口")
@RequestMapping("/item")
public class ItemController extends BaseXCloudController<Item, Long> {

  @Autowired
  private ItemService itemService;

  @Override
  public BaseXCloudService<Item, Long> getService() {
    return itemService;
  }


  @GetMapping(value = "/item/count")
  @ApiOperation(value = "获得商品总数目")
  public BaseResponse getAllItemCount() {

    Long totalCount = itemService.getTotalCount();
    return new BaseResponse(totalCount);
  }

  @PutMapping(value = "/item/stop/{id}")
  @ApiOperation(value = "下架商品")
  public BaseResponse stopItem(@PathVariable Long id) {

    Item item = itemService.alertItemState(id, CommonConstant.STATUS_DISABLE);
    return new BaseResponse(item);
  }

  @PutMapping(value = "/item/start/{id}")
  @ApiOperation(value = "发布商品")
  public BaseResponse startItem(@PathVariable Long id) {

    Item item = itemService.alertItemState(id, CommonConstant.STATUS_NORMAL);
    return new BaseResponse(item);
  }

}
