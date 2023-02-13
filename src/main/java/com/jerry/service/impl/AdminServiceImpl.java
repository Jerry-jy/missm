package com.jerry.service.impl;

import com.jerry.mapper.AdminMapper;
import com.jerry.pojo.Admin;
import com.jerry.pojo.AdminExample;
import com.jerry.service.AdminService;
import com.jerry.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AdminServiceImpl
 * Package: com.jerry.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-12 18:54
 * @Version 1.0
 */

@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层中，一定有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户或到数据库中查询相应用户对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();

        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.size()>0){
            Admin admin = list.get(0);
            //如果查询到用户，再进行密码的密文比对，注意密码是密文的
            /**
             * admin.getApass ==> d033e22ae348aeb5660fc2140aec35850c4da997
             * pwd ==> admin
             * 在进行密码的密文对比时，将用户从前端页面输入的密码pwd进行md5加密，再与数据库中的查到的对象密码进行比对
             */
//            String md5 = MD5Util.getMD5(pwd);
//            if (md5==admin.getaPass()){
//                return admin;
//            }
            return admin;
        }else {
            // 没查到，就返回null
            return null;
        }


    }
}
