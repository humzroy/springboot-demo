package com.example.demo.web.shiro;

import com.example.demo.biz.service.UserService;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.utils.JWTUtil;
import com.example.demo.dao.entity.system.SUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName JWTShiroRealm
 * @Description 用于JWT认证的自定义Realm
 * @Author wuhengzhen
 * @Date 2019-12-16 17:23
 * @Version 1.0
 */
public class JWTShiroRealm extends AuthorizingRealm {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JWTShiroRealm.class);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        // 生成token时保存到redis中的盐
        String secret = redisClient.get("token:" + username);
        if (username == null || !JWTUtil.verify(token, username, secret)) {
            throw new AuthenticationException("token认证失败！");
        }
        SUser user = userService.getJwtTokenInfo(username);
        if (user == null) {
            throw new AuthenticationException("用户名不存在！");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getSalt(), "jwtRealm");
        return authenticationInfo;
    }
}
