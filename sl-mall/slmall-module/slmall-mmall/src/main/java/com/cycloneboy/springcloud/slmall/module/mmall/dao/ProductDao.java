package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import java.util.List;

public interface ProductDao extends BaseXCloudDao<Product, Integer> {

  List<Product> findAllByNameLikeAndStatusAndCategoryIdInOrderByPriceAsc(String productName,
      Integer status,
      List<Integer> categoryIdList);

  List<Product> findAllByStatusAndCategoryIdInOrderByPriceAsc(Integer status,
      List<Integer> categoryIdList);
}