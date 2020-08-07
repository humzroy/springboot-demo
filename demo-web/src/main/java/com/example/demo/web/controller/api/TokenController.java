package com.example.demo.web.controller.api;

import com.example.demo.common.entity.api.*;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.util.DateUtils;
import com.example.demo.common.util.IdUtils;
import com.example.demo.common.util.StringUtils;
import com.example.demo.common.util.api.MD5Util;
import com.example.demo.web.framework.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName TokenController
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 13:59
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private RedisClient redisClient;


    /**
     * API Token
     *
     * @param sign
     * @return
     */
    @PostMapping("/api_token")
    public ApiResponse<AccessToken> apiToken(String appId, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");
        long reqeustInterval = System.currentTimeMillis() - Long.parseLong(timestamp);
        Assert.isTrue(reqeustInterval < 5 * 60 * 1000, "请求过期，请重新请求");
        // 1. 根据appId查询数据库获取appSecret
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        // 2. 校验签名
        String signString = timestamp + appId + appInfo.getKey();
        String signature = MD5Util.encode(signString);
        log.info(signature);
        Assert.isTrue(signature.equals(sign), "签名错误");
        // 3. 如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = this.saveToken(0, appInfo, null);
        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(5000)
    @PostMapping("user_token")
    public ApiResponse<UserInfo> userToken(String username, String password) {
        // 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        UserInfo userInfo = new UserInfo(username, "81255cb0dca1a5f304328a70ac85dcbd", "111111");
        String pwd = password + userInfo.getSalt();
        String passwordMD5 = MD5Util.encode(pwd);
        Assert.isTrue(passwordMD5.equals(userInfo.getPassword()), "密码错误");
        // 2. 保存Token
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    /**
     * 保存token
     *
     * @param tokenType token类型
     * @param appInfo   appInfo
     * @param userInfo  用户信息
     * @return
     */
    private AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = IdUtils.getUppercaseUUID();
        // token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();
        // 4. 保存token
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);
        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }
        this.redisClient.set(token, tokenInfo, 7200);
        AccessToken accessToken = new AccessToken(token, expireTime);
        return accessToken;
    }

    public static void main(String[] args) {
        long timestamp = DateUtils.getNowServerDateLongTime();
        System.out.println(timestamp);
        String signString = timestamp + "1" + "12345678954556";
        String sign = MD5Util.encode(signString);
        System.out.println(sign);
        System.out.println("-------------------");
        signString = "password=123456&username=1&12345678954556" + "ff03e64b-427b-45a7-b78b-47d9e8597d3b1529815393153sdfsdfsfs" + timestamp + "A1scr6";
        sign = MD5Util.encode(signString);
        System.out.println(sign);
    }

}
