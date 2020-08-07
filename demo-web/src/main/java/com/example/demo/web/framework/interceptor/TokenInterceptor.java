package com.example.demo.web.framework.interceptor;

import com.example.demo.common.entity.api.NotRepeatSubmit;
import com.example.demo.common.entity.api.TokenInfo;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.util.StringUtils;
import com.example.demo.common.util.api.MD5Util;
import com.example.demo.web.framework.api.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TokenInterceptor
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 16:31
 * @Version 1.0
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisClient redisClient;

    /**
     * @param request
     * @param response
     * @param handler  访问的目标方法
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        // 随机字符串
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");

        // 获取超时时间
        NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
        long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();

        // 2. 请求时间间隔
        long reqeustInterval = System.currentTimeMillis() - Long.parseLong(timestamp);
        Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");

        // 3. 校验Token是否存在
        TokenInfo tokenInfo = redisClient.get(token);
        Assert.notNull(tokenInfo, "token错误");

        // 4. 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url
        // 请求参数 + token + timestamp + nonce
        String signString = ApiUtil.concatSignString(request) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
        String signature = MD5Util.encode(signString);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");

        // 5. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的才会校验
        if (notRepeatSubmit != null) {
            boolean exists = redisClient.exists(sign);
            Assert.isTrue(!exists, "请勿重复提交");
            redisClient.set(sign, 0, expireTime);
        }

        return super.preHandle(request, response, handler);
    }
}