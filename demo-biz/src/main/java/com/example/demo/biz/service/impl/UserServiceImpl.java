package com.example.demo.biz.service.impl;

import com.example.demo.biz.exception.BizException;
import com.example.demo.biz.service.UserService;
import com.example.demo.common.error.DemoErrors;
import com.example.demo.common.redis.CacheTime;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.utils.DateUtil;
import com.example.demo.common.utils.JWTUtil;
import com.example.demo.common.utils.PasswordUtil;
import com.example.demo.common.utils.StringUtil;
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
     * 注册新用户
     *
     * @param user
     * @return
     */
    @Override
    public int registerUser(SUser user) throws Exception {
        int row = 0;
        if (StringUtil.isNotEmpty(user.getLoginName()) && StringUtil.isNotEmpty(user.getPassword())) {
            SUser userInfo = userMapper.selectByUserName(user.getLoginName());
            if (userInfo != null) {
                log.warn("该用户名已存在！");
                row = -1;
                return row;
            }
            // 密码加密
            String newPassword = PasswordUtil.getEncryptePwd(user.getPassword());
            user.setPassword(newPassword);
            user.setCreateTime(DateUtil.getCurrentDateTime());
            // 保存
            row = userMapper.insertSelective(user);
            log.info("用户：{}注册成功！", user.getLoginName());
        }
        return row;
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
        SUser user = userMapper.selectByPrimaryKey(id);
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
        String jwtSalt = JWTUtil.generateSalt();
        // 将salt保存到缓存中,30分钟后失效
        redisClient.set("token:" + userName, jwtSalt, 1800);
        // 生成jwt token
        return JWTUtil.sign(userName, jwtSalt);
    }

    /**
     * 获取上次token生成时的salt值和登录用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public SUser getJwtTokenInfo(String userName) {
        return getUserInfo(userName);
    }

    /**
     * 清除token信息
     *
     * @param userName 登录用户名
     */
    @Override
    public void deleteLoginInfo(String userName) {
        // 删除数据库或者缓存中保存的salt
        redisClient.del("token:" + userName);
    }

    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     *
     * @param userName
     * @return
     */
    @Override
    public SUser getUserInfo(String userName) {
        return userMapper.selectByUserName(userName);
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
