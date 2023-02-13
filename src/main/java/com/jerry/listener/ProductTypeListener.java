package com.jerry.listener;

import com.jerry.pojo.ProductType;
import com.jerry.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * ClassName: ProductTypeListener
 * Package: com.jerry.listener
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-13 16:47
 * @Version 1.0
 */

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //Spring注册监听器也是通过ContextLoaderListener，因此我们要手动管理ProductTypeListener
        //如果交给Spring管理就不知道哪个Listener先被创建
        //1、手动从Spring容器中取出ProductTypeServiceImpl的对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();

        //2、放入全局作用域中，供新增页面、修改页面、前台的查询功能提供全部的商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
