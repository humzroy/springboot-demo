package com.example.demo.common.entity;

import com.example.demo.common.error.ServiceErrors;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName Result
 * @Description 返回消息javabean
 * @Author wuhengzhen
 * @Date 2019-11-06 13:25
 */
public class Result<T> implements Serializable {
    private T data;

    private boolean success;

    private Integer code;

    private String message;

    public Result() {
    }

    /**
     * 调用成功返回结果
     *
     * @param data 返回数据
     * @return
     */
    public static <T> Result<T> wrapSuccessfulResult(T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.success = true;
        result.code = 200;
        result.message = "";
        return result;
    }

    /**
     * 调用成功返回结果
     *
     * @param message 返回消息
     * @param data    返回数据
     * @return
     */
    public static <T> Result<T> wrapSuccessfulResult(String message, T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.success = true;
        result.code = 200;
        result.message = message;
        return result;
    }

    public static <T> Result<T> wrapErrorResult(ServiceErrors error) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.code = error.getCode();
        result.message = error.getMessage();
        return result;
    }

    public static <T> Result<T> wrapErrorResult(ServiceErrors error, Object... extendMsg) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.code = error.getCode();
        result.message = String.format(error.getMessage(), extendMsg);
        return result;
    }

    /**
     * 调用失败返回结果
     *
     * @param code    返回码
     * @param message 返回消息
     * @return
     */
    public static <T> Result<T> wrapErrorResult(Integer code, String message) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.code = code;
        result.message = message;
        return result;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("success=");
        sb.append(this.success);
        sb.append(",");
        sb.append("code=");
        sb.append(this.code);
        sb.append(",");
        sb.append("message=");
        sb.append(this.message);
        sb.append(",");
        sb.append("data=");
        sb.append(this.data);
        sb.append("}");
        return sb.toString();
    }
}
