package com.example.demo.dao.entity.system;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @ClassName SUser
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-16 16:16
 * @Version 1.0
 */
public class SUser implements Serializable {
    /**
     * id id
     */
    private Integer id;

    /**
     * 登录名 login_name
     */
    private String loginName;

    /**
     * 密码 password
     */
    private String password;

    /**
     * 用户名 user_name
     */
    private String userName;

    /**
     * 角色 role
     */
    private String role;

    /**
     * 权限 permission
     */
    private String permission;

    /**
     * 封号状态 ban
     */
    private String ban;

    /**
     * 手机 mobile
     */
    private String mobile;

    /**
     * 邮箱 email
     */
    private String email;

    /**
     * 创建时间 create_time
     */
    private String createTime;

    /**
     * 创建用户 create_user
     */
    private String createUser;

    /**
     * s_user
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @return id id
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public Integer getId() {
        return id;
    }

    /**
     * id
     *
     * @param id id
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 登录名
     *
     * @return login_name 登录名
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 登录名
     *
     * @param loginName 登录名
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 密码
     *
     * @return password 密码
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     *
     * @param password 密码
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 用户名
     *
     * @return user_name 用户名
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     *
     * @param userName 用户名
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 角色
     *
     * @return role 角色
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getRole() {
        return role;
    }

    /**
     * 角色
     *
     * @param role 角色
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * 权限
     *
     * @return permission 权限
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 权限
     *
     * @param permission 权限
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * 封号状态
     *
     * @return ban 封号状态
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getBan() {
        return ban;
    }

    /**
     * 封号状态
     *
     * @param ban 封号状态
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setBan(String ban) {
        this.ban = ban == null ? null : ban.trim();
    }

    /**
     * 手机
     *
     * @return mobile 手机
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机
     *
     * @param mobile 手机
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 邮箱
     *
     * @return email 邮箱
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     *
     * @param email 邮箱
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 创建时间
     *
     * @return create_time 创建时间
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime 创建时间
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 创建用户
     *
     * @return create_user 创建用户
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建用户
     *
     * @param createUser 创建用户
     * @author wuhen
     * @date 2019-12-16 16:16:13
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @mbg.generated
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
