package com.jerry.controller;

import com.jerry.pojo.Admin;
import com.jerry.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: AdminAction
 * Package: com.jerry.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-12 19:13
 * @Version 1.0
 */

@Controller
@RequestMapping("/admin")
public class AdminAction {
    //切记：在所有的界面层，一定有业务逻辑层的对象
    @Autowired
    AdminService adminService;

    //实现登录判断，并进行相应的跳转
    @RequestMapping("/login.action")
    public String login(String name, String pwd, HttpServletRequest request) {

        Admin admin = adminService.login(name, pwd);
        if (admin != null) {
            //登录成功
//            request.setAttribute("admin", admin);
            request.getSession().setAttribute("admin", admin);
            return "main";

        } else {
            //登录失败
            request.setAttribute("errmsg", "用户名或密码不正确！");
            return "login";
        }

    }

}
