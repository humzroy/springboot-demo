package com.example.demo.common.entity.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName AccessToken
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 15:31
 * @Version 1.0
 */

@Data
@AllArgsConstructor
public class AccessToken {
    /**
     * token
     */
    private String token;
    /**
     * 失效时间
     */
    private Date expireTime;
}
