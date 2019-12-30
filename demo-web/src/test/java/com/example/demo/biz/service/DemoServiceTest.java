package com.example.demo.biz.service;

import com.example.demo.web.DemoWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoServiceTest
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 17:29
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoWebApplication.class})
public class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    StringEncryptor encryptor;

    @Test
    public void test() {
        System.out.println(demoService.test(1));
    }

    @Test
    public void testLog() {
        log.trace("=====trace=====");
        log.debug("=====debug=====");
        log.info("=====这是info级别=====");
        log.warn("=====warn=====");
        log.error("=====error=====");
    }

    @Test
    public void testTransaction() {
        demoService.testTransaction();
    }

    // @Test
    // public void testEncryptor() {
    //     String str1 = "root";
    //     System.out.println("root加密后: "+encryptor.encrypt(str1));
    //     String str2 = "a123456";
    //     System.out.println("a123456加密后: "+encryptor.encrypt(str2));
    //     String str3 = "47.104.9.23:2171";
    //     System.out.println("47.104.9.23:2171加密后: "+encryptor.encrypt(str3));
    //     String str4 = "47.104.9.23:6000,47.104.9.23:6001,47.104.9.23:6002,47.104.9.23:6003,47.104.9.23:6004,47.104.9.23:6005";
    //     System.out.println("加密后: "+encryptor.encrypt(str4));
    //
    // }

}
