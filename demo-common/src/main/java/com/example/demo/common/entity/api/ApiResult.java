package com.example.demo.common.entity.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ApiResult
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 16:03
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
    /**
     * 代码
     */
    private String code;
    /**
     * 结果
     */
    private String msg;
}
