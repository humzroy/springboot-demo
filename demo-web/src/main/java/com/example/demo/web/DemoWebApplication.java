package com.example.demo.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
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
@Slf4j
public class DemoWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    /**
     * Java EE应用服务器配置
     * 如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
     *
     * @param builder
     * @return
     */
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

    /**
     * 实现CommandLineRunner抽象类中的run方法
     * springboot运行后此方法首先被调用
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("************** springboot启动完成 **************");
    }
}
