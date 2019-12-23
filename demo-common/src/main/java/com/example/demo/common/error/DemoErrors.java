package com.example.demo.common.error;

/**
 * Created by wuhengzhen on 2019/11/06.
 */
public enum DemoErrors implements ServiceErrors {
    /**
     * 错误码
     */
    SYSTEM_ERROR(500, "系统错误"),
    PARAM_ERROR(201, "参数错误"),

    USER_IS_NOT_EXIST(404, "用户不存在"),
    USER_UNAUTHORIZED(401, "用户验证失败"),
    USER_REGISTER_FAILD(211, "用户注册失败"),
    USER_ALREADY_EXISTS(210, "用户名已存在"),
    ;
    private Integer code;

    private String message;

    DemoErrors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     *
     * @return Integer
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    @Override
    public String getMessage() {
        return message;
    }
}
