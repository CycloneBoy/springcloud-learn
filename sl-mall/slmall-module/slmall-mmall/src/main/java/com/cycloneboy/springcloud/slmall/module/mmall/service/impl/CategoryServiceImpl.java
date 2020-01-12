package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CategoryDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Category;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 17:51
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

  @Autowired
  private CategoryDao categoryDao;

  @Override
  public ServerResponse addCategory(String categoryName, Integer parentId) {
    return null;
  }

  @Override
  public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
    return null;
  }

  @Override
  public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
    return null;
  }

  @Override
  public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
    return null;
  }

  @Override
  public BaseXCloudDao<Category, Integer> getRepository() {
    return categoryDao;
  }
}
