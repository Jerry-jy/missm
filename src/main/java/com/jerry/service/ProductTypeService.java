package com.jerry.service;

import com.jerry.pojo.ProductType;

import java.util.List;

/**
 * ClassName: ProductTypeService
 * Package: com.jerry.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 16:35
 * @Version 1.0
 */
public interface ProductTypeService {
    List<ProductType> getAll();
}
