package com.jerry.controller;

import com.github.pagehelper.PageInfo;
import com.jerry.pojo.ProductInfo;
import com.jerry.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName: ProductInfoAction
 * Package: com.jerry.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 11:33
 * @Version 1.0
 */

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;
    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    private String getAllProduct(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAllProduct();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的5条记录
    @RequestMapping("/split")
    private String split(HttpServletRequest request) {

        //得到第一页的数据
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return "product";
    }

    //Ajax分页的翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    private void ajaxSplit(int page, HttpSession session) {
        //取得当前page参数的页面数据
        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);

        session.setAttribute("info", info);
    }
}
