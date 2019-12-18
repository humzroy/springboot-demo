package com.example.demo.biz.service.impl;

import com.example.demo.biz.exception.BizException;
import com.example.demo.biz.service.UserService;
import com.example.demo.common.error.DemoErrors;
import com.example.demo.common.redis.CacheTime;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.utils.JWTUtil;
import com.example.demo.dao.entity.UserDO;
import com.example.demo.dao.entity.shiro.User;
import com.example.demo.dao.entity.system.SUser;
import com.example.demo.dao.mapper.business.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName UserServiceImpl
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 15:22
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String encryptSalt = "F12839WhsnnEV$#23b";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    /**
     * 添加用户
     *
     * @param userNick 用户昵称
     * @return Boolean
     */
    @Override
    public Boolean addUser(String userNick) {
        UserDO user = UserDO.builder()
                .userNick(userNick)
                .build();
        return userMapper.insertSelective(user) > 0;
    }

    /**
     * 获取用户字符串
     *
     * @param id 用户ID
     * @return String
     */
    @Override
    public String getUserStr(Integer id) {
        Assert.notNull(id, "id不能为空");
        UserDO user = userMapper.selectById(id);
        if (Objects.isNull(user)) {
            throw new BizException(DemoErrors.USER_IS_NOT_EXIST);
        }
        redisClient.set("user:" + id, user, CacheTime.CACHE_EXP_FIVE_MINUTES);
        return user.toString();
    }

    /**
     * 保存user登录信息，返回token
     *
     * @param userName
     */
    @Override
    public String generateJwtToken(String userName) {
        String salt = "12345";//JwtUtils.generateSalt();
        /**
         * @todo 将salt保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+userName, salt, 3600, TimeUnit.SECONDS);
         */
        // 生成jwt token
        return JWTUtil.sign(userName, salt);
    }

    /**
     * 获取上次token生成时的salt值和登录用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public SUser getJwtTokenInfo(String userName) {
        String salt = "12345";
        /**
         * @todo 从数据库或者缓存中取出jwt token生成时用的salt
         * salt = redisTemplate.opsForValue().get("token:"+userName);
         */
        SUser user = getUserInfo(userName);
        // user.setSalt(salt);
        return user;
    }

    /**
     * 清除token信息
     *
     * @param userName 登录用户名
     */
    @Override
    public void deleteLoginInfo(String userName) {
        /**
         * @todo 删除数据库或者缓存中保存的salt
         * redisTemplate.delete("token:"+userName);
         */
    }

    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     *
     * @param userName
     * @return
     */
    @Override
    public SUser getUserInfo(String userName) {
        SUser user = userMapper.selectByUserName(userName);
        return user;
    }

    /**
     * 获取用户角色列表，强烈建议从缓存中获取
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getUserRoles(String userId) {
        return Arrays.asList("admin");
    }

    @Override
    public String getPassword(String username) {
        return userMapper.getPassword(username);
    }

}
