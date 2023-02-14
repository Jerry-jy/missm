package com.jerry;

import com.jerry.mapper.ProductInfoMapper;
import com.jerry.pojo.ProductInfo;
import com.jerry.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * ClassName: SelectConditionTest
 * Package: com.jerry
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-14 12:24
 * @Version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class SelectConditionTest {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void test(){
        ProductInfoVo vo = new ProductInfoVo();
        //select p_id , p_name, p_content, p_price, p_image, p_number, type_id, p_date from product_info WHERE p_name like '%4%' and type_id = ? and p_price between ? and ? order by p_id desc
        vo.setPname("4");
        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(3999);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        list.forEach(System.out::println);
    }
}
