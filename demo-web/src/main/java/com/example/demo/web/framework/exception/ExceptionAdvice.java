package com.example.demo.web.framework.exception;


import com.aliyuncs.exceptions.ClientException;
import com.example.demo.common.entity.Result;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.exception.LimitException;
import com.example.demo.common.exception.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;


/**
 * @author wuhengzhen
 * @date 2019/8/7 11:09
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 参数校验异常
     *
     * @param e e
     * @return 错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * 阿里短信发送异常
     *
     * @return result
     */
    @ExceptionHandler(ClientException.class)
    public Result handleClientException() {
        return Result.wrapErrorResult(ErrorCodes.SEND_SMS_ERROR);
    }

    /**
     * shiro权限异常处理
     *
     * @return result
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result handleShiroException() {
        return Result.wrapErrorResult(ErrorCodes.NOT_AUTH);
    }


    /**
     * token无效异常
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result handleTokenException() {
        return Result.wrapErrorResult(ErrorCodes.TOKEN_INVALID);
    }


    /**
     * 参数校验异常处理
     *
     * @return result
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingParameterException() {
        return Result.wrapErrorResult(ErrorCodes.MISSING_PARAMETER);
    }


    /**
     * redis异常
     *
     * @return REDIS_CONNECTION_FAILURE
     */
    @ExceptionHandler(RedisException.class)
    public Result handleRedisException() {
        return Result.wrapErrorResult(ErrorCodes.REDIS_CONNECTION_FAILURE);
    }

    /**
     * LimitException
     *
     * @return OUT_OF_LIMIT
     */
    @ExceptionHandler(LimitException.class)
    public Result handleLimitException() {
        return Result.wrapErrorResult(ErrorCodes.OUT_OF_LIMIT);
    }


    /**
     * 捕捉其他所有异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        return Result.wrapErrorResult(getStatus(request).value(), "访问出错，无法访问: " + ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
