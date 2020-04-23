package com.example.demo.common.datasource;

import java.lang.annotation.*;

/**
 * @author wuhengzhen
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default DataSourceConstant.MYSQL;
}
