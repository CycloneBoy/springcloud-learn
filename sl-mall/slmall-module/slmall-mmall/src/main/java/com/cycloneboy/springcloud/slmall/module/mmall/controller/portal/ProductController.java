package com.cycloneboy.springcloud.slmall.module.mmall.controller.portal;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudController;
import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.common.model.PageInfo;
import com.cycloneboy.springcloud.slmall.module.mmall.common.ServerResponse;
import com.cycloneboy.springcloud.slmall.module.mmall.entity.Product;
import com.cycloneboy.springcloud.slmall.module.mmall.service.IProductService;
import com.cycloneboy.springcloud.slmall.module.mmall.vo.ProductDetailVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by geely
 */
@Api(description = "商品接口")
@RestController
@RequestMapping("/product/")
public class ProductController extends BaseXCloudController<Product, Integer> {

    @Autowired
    private IProductService productService;

    /**
     * 查询商品详情
     *
     * @param productId
     * @return
     */
    @GetMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return productService.getProductDetail(productId);
    }

    /**
     * 查询不同栏目的商品列表
     *
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "categoryId", required = false) Integer categoryId,
        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
        @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return productService
            .getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    @Override
    public BaseXCloudService<Product, Integer> getService() {
        return productService;
    }
}
