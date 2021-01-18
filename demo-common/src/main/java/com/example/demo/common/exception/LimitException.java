package com.example.demo.common.exception;

import com.example.demo.common.error.ServiceErrors;

/**
 * @author wuhengzhen
 * @date 2021/01/15 14:39
 **/

public class LimitException extends RuntimeException{

    private final Integer code;

    public LimitException(ServiceErrors errors) {
        super(errors.getMessage());
        this.code = errors.getCode();
    }

    public LimitException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}