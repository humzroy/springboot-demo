package com.example.demo.common.error;

/**
 * Created by wuhengzhen on 2019/11/06.
 */
public enum ErrorCodes implements ServiceErrors {

    // 系统错误状态码
    SYSTEM_ERROR(50001, "系统异常"),
    // Redis ERROR
    REDIS_CONNECTION_FAILURE(50002, "redis操作失败，请检查链接"),
    // 发送短信失败
    SEND_SMS_ERROR(50003, "发送失败"),
    // 接口请求超限
    OUT_OF_LIMIT(50004,"接口请求超限"),
    // 登录失败
    USER_LOGIN_FAIL(40001, "发送失败"),
    // 该用户不存在
    USER_NOT_EXIST(10001, "用户不存在"),
    // 验证码无效
    CODE_EXPIRE(10002, "验证码无效"),
    // 验证码不正确
    CODE_ERROR(10003, "验证码不正确"),
    // 用户名不存在
    USERNAME_NOT_EXIST(10004, "用户名不存在"),
    // 密码不正确
    PASSWORD_ERROR(10005, "密码不正确"),
    // 没有相关权限
    NOT_AUTH(10006, "没有相关权限"),
    // token无效
    TOKEN_INVALID(10007, "token无效或已过期"),
    // 缺少相应参数
    MISSING_PARAMETER(10008, "参数绑定失败:缺少参数或参数类型不正确");

    private Integer code;

    private String message;

    ErrorCodes(Integer code, String message) {
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
