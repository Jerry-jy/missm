package com.jerry;

import com.jerry.utils.MD5Util;
import org.junit.Test;

/**
 * ClassName: myTest
 * Package: com.jerry
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-12 18:48
 * @Version 1.0
 */
public class myTest {
    @Test
    public void testMD5(){
        String md5 = MD5Util.getMD5("admin");
        System.out.println(md5);
        //d033e22ae348aeb5660fc2140aec35850c4da997
        //21232f297a57a5a743894a0e4a801fc3
    }
}
