package com.jerry.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jerry.mapper.ProductInfoMapper;
import com.jerry.pojo.ProductInfo;
import com.jerry.pojo.ProductInfoExample;
import com.jerry.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ProductInfoServiceImpl
 * Package: com.jerry.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 11:26
 * @Version 1.0
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //在业务层一定有数据访问层的对象
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAllProduct() {

        return productInfoMapper.selectByExample(new ProductInfoExample());

    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //分页插件pageHelper工具类完成分页设置
        //SELECT * FROM product_info LIMIT 10,5;
        PageHelper.startPage(pageNum, pageSize);

        //进行PageInfo的数据封装，然后返回一个pageinfo对象就行了
        //1、进行条件查询，必须创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //2、设置排序，按主键降序排序
        //SELECT * FROM product_info ORDER BY p_id DESC;
        example.setOrderByClause("p_id desc");
        //3、排完序后，取集合。切记：一定在取集合前，设置PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //4、将倒序排的集合，封装为PageInfo
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo selectById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }
}
