package com.example.demo.dao.entity.system;

import lombok.Data;
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
@Data
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
     * 盐
     */
    private String salt;

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
     * @mbg.generated
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
