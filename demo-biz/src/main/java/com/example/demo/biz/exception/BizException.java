package com.example.demo.biz.exception;

import com.example.demo.common.error.ServiceErrors;

/**
 * @author wuehengzhen
 * @date 2019/11/06
 */
public class BizException extends RuntimeException {

    private final Integer code;

    public BizException(ServiceErrors errors) {
        super(errors.getMessage());
        this.code = errors.getCode();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
