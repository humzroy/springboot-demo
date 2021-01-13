package com.example.demo.web.framework.configuration;

import com.example.demo.common.util.ThreadPoolUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhengzhen
 * @date 2020/12/18 09:50
 **/
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolUtils getThreadPoolUtils() {
        return new ThreadPoolUtils(ThreadPoolUtils.ThreadEnum.FixedThread, 500);
    }


}