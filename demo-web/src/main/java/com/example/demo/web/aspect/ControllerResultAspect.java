package com.example.demo.web.aspect;

import com.example.demo.common.exception.BizException;
import com.example.demo.common.entity.Result;
import com.example.demo.common.error.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wuhengzhen
 * @date 2019/11/06
 */
@Slf4j
@Aspect
@Component
public class ControllerResultAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)" +
            "&& execution(com.example.demo.common.entity.Result *.*(..))")
    public void controllerResult() {
    }

    @Around("controllerResult()")
    public Result doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result = new Result();
        try {
            result = (Result) proceedingJoinPoint.proceed();
        } catch (BizException e) {
            result.setSuccess(false);
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setCode(ErrorCodes.MISSING_PARAMETER.getCode());
            result.setMessage(e.getMessage());
        } catch (RuntimeException e) {
            log.error("系统出错", e);
            result.setSuccess(false);
            result.setCode(ErrorCodes.SYSTEM_ERROR.getCode());
            result.setMessage(ErrorCodes.SYSTEM_ERROR.getMessage());
        }
        return result;
    }
}
