package com.jerry.service.impl;

import com.jerry.mapper.ProductTypeMapper;
import com.jerry.pojo.ProductType;
import com.jerry.pojo.ProductTypeExample;
import com.jerry.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ProductTypeServiceImpl
 * Package: com.jerry.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 16:40
 * @Version 1.0
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //在业务层一定有数据访问层的对象
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
