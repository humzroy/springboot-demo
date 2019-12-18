package com.example.demo.dao.entity.shiro;

import lombok.Data;

import java.util.List;

/**
 * @ClassName User
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-12 17:51
 * @Version 1.0
 */
@Data
public class User {
    private String userId;

    private String name;

    private String password;

    private String perms;

    private String salt;

    private List<String> roles;
}
