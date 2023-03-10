package com.jerry.service;

import com.github.pagehelper.PageInfo;
import com.jerry.pojo.ProductInfo;
import com.jerry.pojo.vo.ProductInfoVo;

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

    //新增商品
    int save(ProductInfo info);

    //按主键id查询商品
    ProductInfo selectById(int pid);

    //更新商品
    int update(ProductInfo info);

    //单个商品的删除
    int delete(int pid);

    //批量删除商品
    int deleteBatch(String []ids);

    //多条件商品的查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件商品的查询 分页
    PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize);
}
