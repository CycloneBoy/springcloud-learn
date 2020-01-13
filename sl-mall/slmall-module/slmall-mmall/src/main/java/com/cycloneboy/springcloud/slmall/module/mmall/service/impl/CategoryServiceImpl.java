package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CategoryDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Category;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
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

  /**
   * 递归查询本节点的id及孩子节点的id
   *
   * @param categoryId
   * @return
   */
  @Override
  public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
    Set<Category> categorySet = Sets.newHashSet();
    findChildCategory(categorySet, categoryId);

    List<Integer> categoryIdList = Lists.newArrayList();
    if (categoryId != null) {
      for (Category categoryItem : categorySet) {
        categoryIdList.add(categoryItem.getId());
      }
    }
    return ServerResponse.createBySuccess(categoryIdList);
  }

  //递归算法,算出子节点
  private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
    Category category = get(categoryId).orElse(null);
    if (category != null) {
      categorySet.add(category);
    }
    //查找子节点,递归算法一定要有一个退出的条件
    List<Category> categoryList = categoryDao.findAllByParentId(categoryId);
    for (Category categoryItem : categoryList) {
      findChildCategory(categorySet, categoryItem.getId());
    }
    return categorySet;
  }

  @Override
  public BaseXCloudDao<Category, Integer> getRepository() {
    return categoryDao;
  }
}
