package com.example.demo.test;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuhengzhen
 * @date 2021/01/15 17:37
 **/

public class CommonTest {

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Test
    public void testApi() {

        final String url = "http://127.0.0.1:9090/aaron-demo/demo/product/2";
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(() -> System.out.println(HttpUtil.get(url)));
        }
    }
}