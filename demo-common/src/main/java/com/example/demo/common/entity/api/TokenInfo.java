package com.example.demo.common.entity.api;

import lombok.Data;

/**
 * @ClassName TokenInfo
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 16:01
 * @Version 1.0
 */

@Data
public class TokenInfo {
    /**
     * token类型: api:0 、user:1
     */
    private Integer tokenType;
    /**
     * App 信息
     */
    private AppInfo appInfo;
    /**
     * 用户其他数据
     */
    private UserInfo userInfo;
}
