package com.cycloneboy.springcloud.slmall.module.goods.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.goods.dao.ItemCatDao;
import com.cycloneboy.springcloud.slmall.module.goods.entity.ItemCat;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemCatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 10:47
 */
@Slf4j
@Service
public class ItemCatServiceImpl implements ItemCatService {

  @Autowired
  private ItemCatDao itemCatDao;

  @Override
  public BaseXCloudDao<ItemCat, Long> getRepository() {
    return itemCatDao;
  }
}
