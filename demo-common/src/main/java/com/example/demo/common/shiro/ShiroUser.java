package com.example.demo.common.shiro;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName ShiroUser
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-30 15:18
 * @Version 1.0
 */
@Data
@ToString
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private LocalDate birth;
    /**
     * 注册日期
     */
    private LocalDateTime registerTime;
}
