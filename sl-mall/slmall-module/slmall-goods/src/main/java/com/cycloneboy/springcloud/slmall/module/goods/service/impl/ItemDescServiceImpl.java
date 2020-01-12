package com.cycloneboy.springcloud.slmall.module.goods.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.goods.dao.ItemDescDao;
import com.cycloneboy.springcloud.slmall.module.goods.entity.ItemDesc;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemDescService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 10:47
 */
@Slf4j
@Service
public class ItemDescServiceImpl implements ItemDescService {

  @Autowired
  private ItemDescDao itemDescDao;

  @Override
  public BaseXCloudDao<ItemDesc, Long> getRepository() {
    return itemDescDao;
  }
}
