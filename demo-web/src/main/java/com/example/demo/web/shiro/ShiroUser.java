package com.example.demo.web.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ShiroUser
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-16 18:27
 * @Version 1.0
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1737237586764042913L;
    /**
     * 用户ID
     */
    private String userCde;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 盐
     */
    private String salt;

    /**
     * email
     */
    private String email;
}
