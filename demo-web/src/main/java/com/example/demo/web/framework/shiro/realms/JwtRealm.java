package com.example.demo.web.framework.shiro.realms;

import com.example.demo.biz.service.IConUserRoleService;
import com.example.demo.biz.service.IUserService;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.utils.JwtUtil;
import com.example.demo.dao.entity.system.User;
import com.example.demo.common.shiro.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author wuhengzhen
 * @date 2019/8/6 10:02
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;

    @Resource
    private IConUserRoleService userRoleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = userService.selectUserByPhone(JwtUtil.getPhone(principals.toString()));

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        authorizationInfo.addRoles(userRoleService.selectRoleNamesByUserId(user.getUserId()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得token
        String phone = JwtUtil.getPhone(token);
        User user = userService.selectUserByPhone(phone);
        if (user == null || !JwtUtil.verify(token, user.getPassword())) {
            throw new IncorrectCredentialsException(ErrorCodes.TOKEN_INVALID.getMessage());
        }
        return new SimpleAuthenticationInfo(token, token, "JwtRealm");
    }
}
