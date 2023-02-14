package com.jerry.controller;

import com.github.pagehelper.PageInfo;
import com.jerry.pojo.ProductInfo;
import com.jerry.pojo.vo.ProductInfoVo;
import com.jerry.service.ProductInfoService;
import com.jerry.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if (vo != null) {
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        } else {
            //得到第一页的数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }

        request.setAttribute("info", info);
        return "product";
    }

    //Ajax分页的翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        //取得当前page参数的页面数据
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
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

        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (num > 0) {
            request.setAttribute("msg", "增加成功");
        } else {
            request.setAttribute("msg", "增加失败");
        }

        //清空saveFileName这个变量，为了下次新增或修改的异步Ajax的上传处理
        saveFileName = "";
        //增加成功后应该重新访问数据库，所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }

    //根据主键id查询商品
    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo, Model model, HttpSession session) {

        ProductInfo info = productInfoService.selectById(pid);
        model.addAttribute("prod", info);
        //将多条件以及页码放在session中，更新处理结束后，分页时读取条件和页码
        session.setAttribute("prodVo", vo);
        return "update";
    }

    //更新商品
    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request) {
        //1、因为Ajax的异步图片上传，如果有上传过，则 saveFileName 里有上传过来的名称，
        //如果没有使用异步Ajax上传过图片，则saveFileName=""，则实体类使用隐藏表单域提供上来的pImage原始图片的名称；
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        //完成更新处理
        int num = -1;
        //切记：对于增删改的操作，一定要进行try-catch的异常捕获
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (num > 0) {
            //更新成功
            request.setAttribute("msg", "更新成功");
        } else {
            //更新失败
            request.setAttribute("msg", "更新失败");
        }

        //处理完更新后，saveFileName里可能有数据
        //而下一次使用这个变量作为判断的依据，就会出错，所以必须清空saveFileName
        saveFileName = "";
        //redirect会导致request请求丢失，改用forward
        return "forward:/prod/split.action";
    }

    //单个删除
    @RequestMapping("/delete")
    public String delete(int pid, ProductInfoVo vo, HttpServletRequest request) {
        int num = -1;

        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功");
            request.getSession().setAttribute("deleteProductVo", vo);
        } else {
            request.setAttribute("msg", "删除失败");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        //取第一页的数据
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProductVo");
        if (vo != null) {
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
        } else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids, HttpServletRequest request) {
        //将上传上来的字符串截断开，形成商品id的字符数组
        String[] split = pids.split(",");
        int num = -1;
        try {
            num = productInfoService.deleteBatch(split);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            if (num > 0) {
                request.setAttribute("msg", "批量删除成功");
            } else {
                request.setAttribute("msg", "批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "商品不能删除");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    //多条件商品的查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo, HttpSession session) {
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list", list);
    }
}
