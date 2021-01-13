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
 * @Description 接口调用方(客户端)向接口提供方(服务器)申请接口调用账号，申请成功后，接口提供方会给接口调用方一个appId和一个key参数
 * 客户端携带参数appId、timestamp、sign去调用服务器端的API token，其中sign=加密(appId + timestamp + key)
 * 客户端拿着api_token 去访问不需要登录就能访问的接口
 * 当访问用户需要登录的接口时，客户端跳转到登录页面，通过用户名和密码调用登录接口，登录接口会返回一个usertoken, 客户端拿着usertoken 去访问需要登录才能访问的接口
 * sign的作用是防止参数被篡改，客户端调用服务端时需要传递sign参数，服务器响应客户端时也可以返回一个sign用于客户度校验返回的值是否被非法篡改了。客户端传的sign和服务器端响应的sign算法可能会不同。
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
     * 不需要登录访问的接口调用前需要访问此接口获取api_token
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

    /**
     * 需要登录的接口调用前，需调用此接口获取user_token
     *
     * @param username
     * @param password
     * @return
     */
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
        signString = "password=123456&username=1&12345678954556" + "CB0CCAA24C664B8C8FC469E66915E87D" + timestamp + "A1scr6";
        sign = MD5Util.encode(signString);
        System.out.println(sign);
    }

}
