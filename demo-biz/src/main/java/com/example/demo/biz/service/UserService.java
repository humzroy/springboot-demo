package com.example.demo.biz.service;

import com.example.demo.dao.entity.shiro.User;
import com.example.demo.dao.entity.system.SUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 15:22
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param userNick 用户昵称
     * @return Boolean
     */
    Boolean addUser(String userNick);

    /**
     * 获取用户字符串
     *
     * @param id 用户ID
     * @return String
     */
    String getUserStr(Integer id);

    /**
     * 保存user登录信息，返回token
     *
     * @param userName
     */
    public String generateJwtToken(String userName);

    /**
     * 获取上次token生成时的salt值和登录用户信息
     *
     * @param userName
     * @return
     */
    public SUser getJwtTokenInfo(String userName);

    /**
     * 清除token信息
     *
     * @param userName 登录用户名
     */
    public void deleteLoginInfo(String userName);

    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     *
     * @param userName
     * @return
     */
    SUser getUserInfo(String userName);

    /**
     * 获取用户角色列表，强烈建议从缓存中获取
     *
     * @param userId
     * @return
     */
    public List<String> getUserRoles(String userId);

    String getPassword(String username);
}
