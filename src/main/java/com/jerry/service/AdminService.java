package com.jerry.service;

import com.jerry.pojo.Admin;

/**
 * ClassName: AdminService
 * Package: com.jerry.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-12 18:52
 * @Version 1.0
 */
public interface AdminService{
    //完成登录判断
    Admin login(String name, String pwd);
}
