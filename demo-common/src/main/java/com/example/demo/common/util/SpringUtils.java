package com.example.demo.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringUtils
 * @Description Spring上下文工具类
 * @Author wuhengzhen
 * @Date 2019-12-30 15:09
 * @Version 1.0
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
     *
     * @author wuhengzhen
     * @date 2019/12/30 16:58
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 通过Name返回指定的Bean
     *
     * @author wuhengzhen
     * @date 2019/12/30 16:58
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return context.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return context.getType(name);
    }

}
