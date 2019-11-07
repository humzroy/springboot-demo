package com.example.demo.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoWebApplication
 * @Description 入口类
 * @Author wuhengzhen
 * @Date 2019-11-06 10:02
 */
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.example.demo")
@DubboComponentScan(basePackages = "com.example.demo.biz.service.impl.remote")
@MapperScan("com.example.demo.dao.mapper")
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
