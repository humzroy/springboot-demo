package com.example.demo.web.shiro;

import com.example.demo.biz.service.UserService;
import com.example.demo.dao.entity.system.SUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
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

    //数据库存储的用户密码的加密salt，正式环境不能放在源代码里
    private static final String encryptSalt = "F12839WhsnnEV$#23b";

    @Autowired
    private UserService userService;


    /**
     * 找它的原因是这个方法返回true
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 这一步我们根据token给的用户名，去数据库查出加密过用户密码，然后把加密后的密码和盐值一起发给shiro，让它做比对
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken)authenticationToken;
        String username = userpasswordToken.getUsername();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (username == null) {
            throw new AuthenticationException("用户名不能为空！");
        }
        SUser user = userService.getUserInfo(username);
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                throw new IncorrectCredentialsException("密码错误！");
            }
        } else {
            throw new AuthenticationException("用户不存在！");
        }
        Object credentials = user.getPassword();
        ShiroUser principal = new ShiroUser();
        principal.setUserCde(username);
        principal.setPassword(user.getPassword());
        principal.setSalt(encryptSalt);
        principal.setUserName(user.getUserName());
        principal.setEmail(user.getEmail());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, "shiroRealm");
        return info;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
