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

    /**
     * Jasypt加解密
     */
    @Test
    public void testJasypt() {
        //对应配置文件中对应的 加密的密匙
        System.setProperty("jasypt.encryptor.password", "ckVLnPu4xLJ2MAF5Ru9");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        //加密方法
        System.out.println(stringEncryptor.encrypt("a123456"));
        // //解密方法
        // System.out.println(stringEncryptor.decrypt("POC0CDdJLtuRHpULrgLb1DyhFf2DTbOa3OCYzIxBVxNEVh3fSJXK2HHUdHBhgkd9"));

        String str = "POC0CDdJLtuRHpULrgLb1DyhFf2DTbOa3OCYzIxBVxNEVh3fSJXK2HHUdHBhgkd9";
        System.out.println("*********：" + stringEncryptor.decrypt(str));
        String pswd = "cEbIlwYY7hdnuC+CqMQh91vGp4CR8EUGE+rTlSj96X400vf0/g4THOhL2OqvE6vkgk88UJFseEPvkcKhln7fhSvagNHKluYwwgxK0Rv4wuM0dgpg85jzp9+jyULt5vJgKHBa3zqNZFbjTEDO4NbBQS2osC7exf5JtlOM/6VllDEDMf/DR0kHNnD6nOf+gpIL";
        System.out.println("解密后：" + stringEncryptor.decrypt(pswd));
    }
}
