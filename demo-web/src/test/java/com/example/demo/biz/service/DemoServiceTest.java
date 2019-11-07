package com.example.demo.biz.service;

import com.example.demo.web.DemoWebApplication;
import lombok.extern.slf4j.Slf4j;
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

}
