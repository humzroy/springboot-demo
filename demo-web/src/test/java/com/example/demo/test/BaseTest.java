package com.example.demo.test;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;

/**
 * @ClassName BaseTest
 * @Description
 * @Author wuhengzhen
 * @Date 2020-06-23 10:01
 * @Version 1.0
 */
public class BaseTest {

    @Test
    public void testJasypt() {
        //对应配置文件中对应的 加密的密匙
        System.setProperty("jasypt.encryptor.password", "ckVLnPu4xLJ2MAF5Ru9");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        //加密方法
        System.out.println(stringEncryptor.encrypt("47.104.9.23:3181"));
        // //解密方法
        // System.out.println(stringEncryptor.decrypt("POC0CDdJLtuRHpULrgLb1DyhFf2DTbOa3OCYzIxBVxNEVh3fSJXK2HHUdHBhgkd9"));

        String pswd = "feY8/uoAf6Ew3/XF3p3eL6hTmlEdTiYRYQ6X/B5RX5t7bxBf3M7knCO3pgNxwSs1hECs10V3L+L0vy/wKfdMEg==";
        System.out.println("解密后：" + stringEncryptor.decrypt(pswd));
    }
}
