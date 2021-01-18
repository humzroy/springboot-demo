package com.example.demo.web.framework.api;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD}) //方法级别
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 为了方便管理各个接口的限流值，这里是设置对应application.properties里的属性key，key对应的value才是限流值
     */
    String limitKey() default "api.default.limit";
}
