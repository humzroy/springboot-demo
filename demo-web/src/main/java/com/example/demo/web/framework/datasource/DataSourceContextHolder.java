package com.example.demo.web.framework.datasource;

import com.example.demo.common.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName DataSourceContextHolder
 * @Description 线程持有数据源上线文
 * @Author wuhengzhen
 * @Date 2020-01-16 11:47
 * @Version 1.0
 */
public class DataSourceContextHolder {
    /**
     * 当前线程对应的数据源
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 存储当前系统加载的数据源的查找键（look up key）KEY
     */
    public static final Set<Object> ALL_DATA_SOURCE_KEY = new HashSet<>();

    /**
     * 设置当前线程持有的数据源
     */
    public static void setDataSource(String dataSource) {
        if (isExist(dataSource)) {
            CONTEXT_HOLDER.set(dataSource);
        } else {
            throw new NullPointerException(StringUtils.join("数据源查找键（Look up key）【", dataSource, "】不存在"));
        }
    }

    /**
     * 获取当前线程持有的数据源
     */
    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 删除当前线程持有的数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断数据源在系统中是否存在
     */
    public static boolean isExist(String dataSource) {
        if (StringUtils.isEmpty(dataSource)) {
            return false;
        }
        if (ALL_DATA_SOURCE_KEY.contains(dataSource)) {
            return true;
        }
        return false;
    }
}
