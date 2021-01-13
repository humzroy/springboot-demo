package com.example.demo.web.framework.configuration;

import com.example.demo.web.framework.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName WebMvcConfiguration
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 16:29
 * @Version 1.0
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 不需要拦截的api
     */
    private static final String[] EXCLUDE_PATH_PATTERNS = {"/api/token/api_token", "/api/token/user_token"};

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS);
    }
}