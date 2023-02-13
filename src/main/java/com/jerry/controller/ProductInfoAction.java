package com.jerry.controller;

import com.github.pagehelper.PageInfo;
import com.jerry.pojo.ProductInfo;
import com.jerry.service.ProductInfoService;
import com.jerry.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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

    //异步上传的文件图片的名称
    String saveFileName = "";

    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAllProduct(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAllProduct();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的5条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {

        //得到第一页的数据
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return "product";
    }

    //Ajax分页的翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(int page, HttpSession session) {
        //取得当前page参数的页面数据
        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);

        session.setAttribute("info", info);
    }

    //异步Ajax文件上传处理
    @ResponseBody
    @RequestMapping("ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {

        //1、提取、生成文件名UUID+上传图片后缀名.jpg .png
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //2、获取图片的存取路径
        String path = request.getServletContext().getRealPath("/image_big");
        //3、转存
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //返回客户端的JSON对象， 封装图片路径，为了在页面上回显图片
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);

        return object.toString();
    }

    //新增商品
    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request) {
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int num=-1;
        try {
            num=productInfoService.save(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (num>0){
            request.setAttribute("msg", "增加成功");
        }else {
            request.setAttribute("msg", "增加失败");
        }

        //增加成功后应该重新访问数据库，所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }
}
