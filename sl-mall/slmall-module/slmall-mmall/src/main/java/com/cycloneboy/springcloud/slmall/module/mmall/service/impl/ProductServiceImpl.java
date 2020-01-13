package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.constant.CommonConstant;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.Const;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ResponseCode;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.CategoryDao;
import com.cycloneboy.springcloud.slmall.module.mmall.dao.ProductDao;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Category;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import com.cycloneboy.springcloud.slmall.module.mmall.service.ICategoryService;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.DateTimeUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.utils.PropertiesUtil;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.ProductDetailVo;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.ProductListVo;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
  private ICategoryService categoryService;


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
    if (productId == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }
    Product product = get(productId).orElse(null);
    if (product == null) {
      return ServerResponse.createByErrorMessage("产品已下架或者删除");
    }
    if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
      return ServerResponse.createByErrorMessage("产品已下架或者删除");
    }
    ProductDetailVo productDetailVo = assembleProductDetailVo(product);
    return ServerResponse.createBySuccess(productDetailVo);
  }


  private ProductDetailVo assembleProductDetailVo(Product product) {
    ProductDetailVo productDetailVo = new ProductDetailVo();

    BeanUtils.copyProperties(product, productDetailVo);

    productDetailVo.setImageHost(
        PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

    Category category = categoryService.get(product.getCategoryId()).orElse(null);
    if (category == null) {
      productDetailVo.setParentCategoryId(0);//默认根节点
    } else {
      productDetailVo.setParentCategoryId(category.getParentId());
    }

    productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
    productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
    return productDetailVo;
  }

  @Override
  public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
      int pageNum, int pageSize, String orderBy) {

    if (StringUtils.isBlank(keyword) && categoryId == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT);
    }

    List<Integer> categoryIdList = new ArrayList<Integer>();

    if (categoryId != null) {
      Category category = categoryService.get(categoryId).orElse(null);
      if (category == null && StringUtils.isBlank(keyword)) {
        //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
        List<ProductListVo> productListVoList = Lists.newArrayList();
        PageInfo pageInfo = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
      }

      categoryIdList = categoryService.selectCategoryAndChildrenById(category.getId()).getData();
    }

    List<Product> productList = new ArrayList<>();
    if (StringUtils.isNotBlank(keyword)) {
      productList = productDao
          .findAllByNameLikeAndStatusAndCategoryIdInOrderByPriceAsc(keyword,
              CommonConstant.STATUS_NORMAL, categoryIdList);
    } else {
      productList = productDao
          .findAllByStatusAndCategoryIdInOrderByPriceAsc(CommonConstant.STATUS_NORMAL,
              categoryIdList);
    }

    //排序处理

    List<ProductListVo> productListVoList = Lists.newArrayList();
    for (Product product : productList) {
      ProductListVo productListVo = assembleProductListVo(product);
      productListVoList.add(productListVo);
    }

    PageInfo pageInfo = new PageInfo(productList);
    pageInfo.setList(productListVoList);
    return ServerResponse.createBySuccess(pageInfo);
  }


  private ProductListVo assembleProductListVo(Product product) {
    ProductListVo productListVo = new ProductListVo();

    BeanUtils.copyProperties(product, productListVo);
    productListVo.setImageHost(
        PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

    return productListVo;
  }
}
