package com.example.demo.biz.service.impl;

import com.example.demo.biz.exception.BizException;
import com.example.demo.biz.service.UserService;
import com.example.demo.common.error.DemoErrors;
import com.example.demo.common.redis.CacheTime;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.dao.entity.UserDO;
import com.example.demo.dao.mapper.business.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
}
