package com.example.demo.web.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName JWTToken
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-12 17:46
 * @Version 1.0
 */
public class JWTToken implements AuthenticationToken {
    /**
     * 秘钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
