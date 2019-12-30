package com.example.demo.common.constant;

/**
 * 常量类
 *
 * @author wuhengzhen
 * @date 2019/10/3 15:45
 */
public class Constant {

    /**
     * 验证码过期时间 此处为五分钟
     */
    public static final Integer CODE_EXPIRE_TIME = 60 * 5;

    /**
     * jwtToken过期时间
     */
    public static final Long TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    /**
     * token请求头名称
     */
    public static final String TOKEN_HEADER_NAME = "authorization";

    /**
     * 锁定
     */
    public static final String USER_STATUS_LOCKED = "2";

    /**
     * 未激活
     */
    public static final String USER_STATUS_UNACTIVATED_LOCKED = "5";
}
