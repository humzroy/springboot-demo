package com.example.demo.web.framework.aspect;

import com.example.demo.common.datasource.DataSource;
import com.example.demo.common.datasource.DataSourceConstant;
import com.example.demo.common.datasource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName DynamicDataSourceAspect
 * @Description
 * @Author wuhengzhen
 * @Date 2020-01-20 10:41
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect implements Ordered {

    /**
     * 注意：@interface DataSource这个注解DataSource的包名
     */
    // 拦截所有service方法
    // @Pointcut("execution(* com.example.demo.*.service..*.*(..))")
    @Pointcut("execution(* com.example.demo.*.service..*.*(..))")
    public void dataSourcePointCut() {

    }

    @SuppressWarnings("rawtypes")
    @Before("dataSourcePointCut()")
    public void beforeSwitchDataSource(JoinPoint point) {
        log.info("before......");

        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
        log.info("using dataSource:{}", DataSourceContextHolder.getDataSource());
    }


    @After("dataSourcePointCut()")
    public void afterSwitchDataSource() {
        log.info("after......");
        DataSourceContextHolder.clearDataSource();
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            log.error("dataSource annotation error:{}", e.getMessage());
            // 若出现异常，手动设为MYSQL库
            DataSourceContextHolder.setDataSource(DataSourceConstant.MYSQL);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
