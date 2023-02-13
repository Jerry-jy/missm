package com.jerry.service;

import com.github.pagehelper.PageInfo;
import com.jerry.pojo.ProductInfo;

import java.util.List;

/**
 * ClassName: ProductInfoService
 * Package: com.jerry.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 11:21
 * @Version 1.0
 */
public interface ProductInfoService {
    //显示全部商品不分页
    List<ProductInfo> getAllProduct();

    //商品分页显示
    //使用mybits提供的插件，返回值是PageInfo，形参传入（当前页，每页显示条数）
    PageInfo splitPage(int pageNum, int pageSize);
}
