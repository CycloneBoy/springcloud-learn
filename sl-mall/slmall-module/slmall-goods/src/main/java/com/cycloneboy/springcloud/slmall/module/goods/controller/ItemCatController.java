package com.cycloneboy.springcloud.slmall.module.goods.controller;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudController;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.module.goods.entity.ItemCat;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemCatService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2020-01-12 11:30
 */
@Slf4j
@RestController
@Api(description = "商品类别接口")
@RequestMapping("/itemcat")
public class ItemCatController extends BaseXCloudController<ItemCat, Long> {

  @Autowired
  private ItemCatService itemCatService;

  @Override
  public BaseXCloudService<ItemCat, Long> getService() {
    return itemCatService;
  }
}
