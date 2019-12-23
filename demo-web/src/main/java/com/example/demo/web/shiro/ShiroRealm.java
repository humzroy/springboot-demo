package com.example.demo.web.shiro;

import com.example.demo.biz.service.UserService;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.utils.PasswordUtil;
import com.example.demo.dao.entity.system.SUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ShiroRealm
 * @Description 自定义身份认证Shiro
 * @Author wuhengzhen
 * @Date 2019-12-12 17:41
 * @Version 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserService userService;


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = userpasswordToken.getUsername();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (username == null) {
            throw new AuthenticationException("用户名不能为空！");
        }
        SUser user = userService.getUserInfo(username);
        if (user != null) {
            try {
                if (!PasswordUtil.verifyPwd(password, user.getPassword())) {
                    throw new IncorrectCredentialsException("密码错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IncorrectCredentialsException("校验密码出现异常！" + e.getMessage());
            }
        } else {
            throw new AuthenticationException("用户不存在！");
        }
        Object credentials = user.getPassword();
        ShiroUser principal = new ShiroUser();
        principal.setUserCode(username);
        principal.setPassword(user.getPassword());
        principal.setUserName(user.getUserName());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, password, "shiroRealm");
        return info;
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }
}
