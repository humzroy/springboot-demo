package com.example.demo.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoWebApplication
 * @Description 入口类
 * @Author wuhengzhen
 * @Date 2019-11-06 10:02
 */
@SpringBootApplication(scanBasePackages = {"com.example.demo"}, exclude = {DataSourceAutoConfiguration.class})
@DubboComponentScan(basePackages = "com.example.demo.biz.service.impl.remote")
public class DemoWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoWebApplication.class);
    }

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplication.class, args);
    }
}
