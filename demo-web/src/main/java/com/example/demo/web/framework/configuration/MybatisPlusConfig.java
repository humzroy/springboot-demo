package com.example.demo.web.framework.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wuhengzhen
 * @date 2019/7/30 17:26
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.example.demo.dao.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件，自动识别数据库类型
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
