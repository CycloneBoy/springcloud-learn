package com.cycloneboy.springcloud.slmall.module.mmall.dao;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Category;
import java.util.List;

public interface CategoryDao extends BaseXCloudDao<Category, Integer> {

  List<Category> findAllByParentId(Integer parentId);
}