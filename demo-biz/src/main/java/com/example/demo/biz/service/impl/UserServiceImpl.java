package com.example.demo.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.biz.service.IConUserRoleService;
import com.example.demo.biz.service.IUserService;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.constant.ConstantRedisKey;
import com.example.demo.common.entity.Result;
import com.example.demo.common.enums.LoginType;
import com.example.demo.common.enums.RoleEnums;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.shiro.token.CustomizedToken;
import com.example.demo.common.utils.CommonsUtils;
import com.example.demo.common.utils.JwtUtil;
import com.example.demo.dao.entity.system.User;
import com.example.demo.dao.mapper.business.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RedisClient redisClient;

    @Resource
    private IConUserRoleService conUserRoleService;


    @Override
    public Result sendLoginCode(String phone) {
        // 这里使用默认值，随机验证码的方法为CommonsUtils.getCode()
        int code = 666666;
        // todo 此处为发送验证码代码
        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        redisClient.set(ConstantRedisKey.getLoginCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME);
        return Result.wrapSuccessfulResult(null);
    }

    @Override
    public Result loginByPassword(String phone, String password) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        User sysUser = this.selectUserByPhone(phone);
        // 2.密码登录
        if (Objects.isNull(sysUser)) {
            // 3.1 返回该用户不存在
            return Result.wrapErrorResult(ErrorCodes.USER_NOT_EXIST);
        }
        if (!subject.isAuthenticated()) {
            // 3.封装用户数据
            CustomizedToken token = new CustomizedToken(phone, password, LoginType.PASSWORD_LOGIN_TYPE.toString());
            // 4.执行登录方法
            try {
                subject.login(token);
                Map<String, Object> data = returnLoginInitParam(phone);
                return Result.wrapSuccessfulResult(data);
            } catch (UnknownAccountException e) {
                return Result.wrapErrorResult(ErrorCodes.USERNAME_NOT_EXIST);
            } catch (ExpiredCredentialsException e) {
                return Result.wrapErrorResult(ErrorCodes.CODE_EXPIRE);
            } catch (IncorrectCredentialsException e) {
                return Result.wrapErrorResult(ErrorCodes.PASSWORD_ERROR);
            }
        } else {
            Map<String, Object> data = returnLoginInitParam(phone);
            return Result.wrapSuccessfulResult(data);
        }

    }

    @Override
    public Result loginByCode(String phone, String code) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        User sysUser = this.selectUserByPhone(phone);
        // 2.验证码登录，如果该用户不存在则创建该用户
        if (Objects.isNull(sysUser)) {
            // 2.1 注册
            this.register(phone);
        }
        if (!subject.isAuthenticated()) {
            // 3.封装用户数据
            CustomizedToken token = new CustomizedToken(phone, code, LoginType.CODE_LOGIN_TYPE.toString());
            // 4.执行登录方法
            try {
                subject.login(token);
                Map<String, Object> data = returnLoginInitParam(phone);
                return Result.wrapSuccessfulResult(data);
            } catch (UnknownAccountException e) {
                return Result.wrapErrorResult(ErrorCodes.USERNAME_NOT_EXIST);
            } catch (ExpiredCredentialsException e) {
                return Result.wrapErrorResult(ErrorCodes.CODE_EXPIRE);
            } catch (IncorrectCredentialsException e) {
                return Result.wrapErrorResult(ErrorCodes.CODE_ERROR);
            }
        } else {
            Map<String, Object> data = returnLoginInitParam(phone);
            return Result.wrapSuccessfulResult(data);
        }

    }

    /**
     * 返回登录后初始化参数
     *
     * @param phone phone
     * @return Map
     */
    private Map<String, Object> returnLoginInitParam(String phone) {
        Map<String, Object> data = new HashMap<>(1);
        User user = selectUserByPhone(phone);
        // 生成jwtToken
        String jwtToken = JwtUtil.sign(phone, user.getUserId(), user.getPassword());
        // token
        data.put("jwtToken", jwtToken);
        return data;
    }

    /**
     * 用户注册,默认密码为手机号后六位
     *
     * @param phone phone
     */
    private void register(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setRegisterTime(LocalDateTime.now());
        String encryptPassword = CommonsUtils.encryptPassword(phone.substring(5, 11), phone);
        user.setPassword(encryptPassword);
        this.save(user);
        conUserRoleService.connectUserRole(user.getUserId(), RoleEnums.ROLE1.getCode());
    }
}
