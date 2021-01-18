package com.example.demo.web.framework.aspect;

import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.exception.LimitException;
import com.example.demo.common.util.StringUtils;
import com.example.demo.web.framework.api.RateLimit;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 针对注解@RateLimit的切面（限流的核心）
 *
 * @author wuhengzhen
 * @date 2021/01/15 14:25
 **/
@Component
@Aspect
@Slf4j
public class RateLimitAspect {

    @Autowired
    private Environment env;

    //用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
    private ConcurrentHashMap<String, RateLimiter> rateLimitMap = new ConcurrentHashMap<>();


    /**
     * 针对含有该注解的地方进行切入
     */
    @Pointcut("@annotation(com.example.demo.web.framework.api.RateLimit)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //限流判断，和限流处理
        Object obj = null;
        //获取拦截的签名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        //在application.properties中获取注解每秒加入桶中的token
        String limitNum;
        if (StringUtils.isEmpty(annotation.limitKey())) {
            limitNum = env.getProperty(annotation.limitKey());
        } else {
            limitNum = annotation.limitKey();
        }

        // 注解所在方法名区分不同的限流策略
        String functionName = msig.toShortString();
        //如果值为0，则为不进行限流
        if (limitNum == null || limitNum.equals("0")) {
            obj = joinPoint.proceed();
        } else {
            //获取rateLimiter
            if (!rateLimitMap.containsKey(functionName)) {
                //使用最简洁的方法来创建RateLimiter，RateLimiter.create(double xx)，如果有需要，可自行设置RateLimiter其他属性
                rateLimitMap.put(functionName, RateLimiter.create(Double.parseDouble(limitNum)));
            }
            RateLimiter rateLimiter = rateLimitMap.get(functionName);
            //尝试获得一个令牌
            if (rateLimiter.tryAcquire(1)) {
                //执行方法
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级），这是自己定义的异常类
                throw new LimitException(ErrorCodes.OUT_OF_LIMIT);
                // throw new BizException("拒绝了访问open api方法的请求", FieldCodeEnum.OPEN_API, null, DetailCodeEnum.ACCESS, ErrorCodeEnum.OUT_OF_LIMIT);
            }
        }
        return obj;
    }
}