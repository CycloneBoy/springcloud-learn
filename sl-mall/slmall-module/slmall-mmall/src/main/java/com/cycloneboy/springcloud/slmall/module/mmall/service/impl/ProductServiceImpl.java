package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CategoryDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ProductDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICategoryService;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.ProductDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2020-01-12 16:33
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

  @Autowired
  private ProductDao productDao;

  @Autowired
  private CategoryDao categoryDao;

  @Autowired
  private ICategoryService iCategoryService;


  @Override
  public BaseXCloudDao<Product, Integer> getRepository() {
    return productDao;
  }

  @Override
  public ServerResponse saveOrUpdateProduct(Product product) {
    return null;
  }

  @Override
  public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
    return null;
  }

  @Override
  public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum,
      int pageSize) {
    return null;
  }

  @Override
  public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
    return null;
  }

  @Override
  public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
      int pageNum, int pageSize, String orderBy) {
    return null;
  }
}
