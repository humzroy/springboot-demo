package com.example.demo.web.framework.shiro.realms;

import com.example.demo.biz.service.system.IConUserRoleService;
import com.example.demo.biz.service.system.ILoginService;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.shiro.token.JwtToken;
import com.example.demo.common.utils.JwtUtils;
import com.example.demo.common.utils.StringUtils;
import com.example.demo.dao.entity.system.User;
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
    private ILoginService loginService;

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
        User user = loginService.selectUserByPhone(JwtUtils.getPhone(principals.toString()));

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        authorizationInfo.addRoles(userRoleService.selectRoleNamesByUserId(user.getUserId()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得token
        String phone = JwtUtils.getPhone(token);
        if (StringUtils.isEmpty(phone)) {
            throw new AuthenticationException("token无效");
        }
        User user = loginService.selectUserByPhone(phone);
        if (user == null || !JwtUtils.verify(token, user.getPassword())) {
            throw new IncorrectCredentialsException(ErrorCodes.TOKEN_INVALID.getMessage());
        }
        return new SimpleAuthenticationInfo(token, token, "JwtRealm");
    }
}
