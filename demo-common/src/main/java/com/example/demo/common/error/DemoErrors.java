package com.example.demo.common.error;

/**
 * Created by wuhengzhen on 2019/11/06.
 */
public enum DemoErrors implements ServiceErrors {
    /**
     * 错误码
     */
    SYSTEM_ERROR(10000, "系统错误"),
    PARAM_ERROR(10001, "参数错误"),

    USER_IS_NOT_EXIST(20000, "用户不存在"),
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
        return null;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    @Override
    public String getMessage() {
        return null;
    }
}
