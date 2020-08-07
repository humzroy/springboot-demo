package com.example.demo.common.entity.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName AppInfo
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 15:32
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {
    /**
     * App id
     */
    private String appId;
    /**
     * API 秘钥
     */
    private String key;
}