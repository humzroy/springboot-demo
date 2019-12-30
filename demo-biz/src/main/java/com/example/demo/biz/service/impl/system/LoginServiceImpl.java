package com.example.demo.biz.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.biz.service.system.IConUserRoleService;
import com.example.demo.biz.service.system.ILoginService;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.constant.ConstantRedisKey;
import com.example.demo.common.entity.Result;
import com.example.demo.common.enums.LoginType;
import com.example.demo.common.enums.RoleEnums;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.redis.RedisClient;
import com.example.demo.common.shiro.ShiroUtils;
import com.example.demo.common.shiro.token.CustomizedToken;
import com.example.demo.common.utils.CommonsUtils;
import com.example.demo.common.utils.JwtUtils;
import com.example.demo.common.utils.NameUtils;
import com.example.demo.dao.entity.system.User;
import com.example.demo.dao.mapper.business.system.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName LoginServiceImpl
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 15:17
 * @Version 1.0
 */
@Slf4j
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Resource
    private RedisClient redisClient;

    @Resource
    private IConUserRoleService conUserRoleService;


    @Override
    public Result sendLoginCode(String phone) {
        // 获得6位随机验证码
        int code = CommonsUtils.getCode();
        // todo 此处为发送验证码代码
        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        redisClient.set(ConstantRedisKey.getLoginCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME);
        // 这里直接将验证码返回，实际应走email或者短信
        return Result.wrapSuccessfulResult("发送验证码成功", code);
    }

    @Override
    public Result loginByPassword(String phone, String password) {
        User sysUser = this.selectUserByPhone(phone);
        // 1.密码登录
        if (Objects.isNull(sysUser)) {
            // 3.1 返回该用户不存在
            return Result.wrapErrorResult(ErrorCodes.USER_NOT_EXIST);
        }
        // 2.封装用户数据
        CustomizedToken token = new CustomizedToken(phone, password, LoginType.PASSWORD_LOGIN_TYPE.toString());
        // 3.执行登录方法
        try {
            ShiroUtils.getSubject().login(token);
            if (ShiroUtils.isLogin()) {
                log.info("[密码登录] {}登录成功！", phone);
                Map<String, Object> data = returnLoginInitParam(phone);
                return Result.wrapSuccessfulResult("登录成功", data);
            } else {
                log.error("[密码登录] {}登录失败", phone);
                return Result.wrapErrorResult(ErrorCodes.USER_LOGIN_FAIL);
            }
        } catch (UnknownAccountException e) {
            return Result.wrapErrorResult(ErrorCodes.USERNAME_NOT_EXIST);
        } catch (ExpiredCredentialsException e) {
            return Result.wrapErrorResult(ErrorCodes.CODE_EXPIRE);
        } catch (IncorrectCredentialsException e) {
            return Result.wrapErrorResult(ErrorCodes.PASSWORD_ERROR);
        }

    }

    @Override
    public Result loginByCode(String phone, String code) {
        User sysUser = this.selectUserByPhone(phone);
        // 1.验证码登录，如果该用户不存在则创建该用户
        if (Objects.isNull(sysUser)) {
            // 2.1 注册
            this.register(phone);
        }
        // 2.封装用户数据
        CustomizedToken token = new CustomizedToken(phone, code, LoginType.CODE_LOGIN_TYPE.toString());
        // 3.执行登录方法
        try {
            ShiroUtils.getSubject().login(token);
            if (ShiroUtils.isLogin()) {
                log.info("[验证码登录] {}登录成功！", phone);
                Map<String, Object> data = returnLoginInitParam(phone);
                return Result.wrapSuccessfulResult("登录成功", data);
            } else {
                log.error("[验证码登录] {}登录失败", phone);
                return Result.wrapErrorResult(ErrorCodes.USER_LOGIN_FAIL);
            }
        } catch (UnknownAccountException e) {
            return Result.wrapErrorResult(ErrorCodes.USERNAME_NOT_EXIST);
        } catch (ExpiredCredentialsException e) {
            return Result.wrapErrorResult(ErrorCodes.CODE_EXPIRE);
        } catch (IncorrectCredentialsException e) {
            return Result.wrapErrorResult(ErrorCodes.CODE_ERROR);
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
        String jwtToken = JwtUtils.sign(phone, user.getUserId(), user.getPassword());
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
        // 生成一个随机的用户名
        user.setName(NameUtils.getRandomNickNameCN(4));
        user.setRegisterTime(LocalDateTime.now());
        // 默认密码为手机号后六位, MD5加密
        String encryptPassword = CommonsUtils.encryptPassword(phone.substring(5, 11), phone);
        user.setPassword(encryptPassword);
        this.save(user);
        // 默认角色-普通用户
        conUserRoleService.connectUserRole(user.getUserId(), RoleEnums.ROLE_NORMAL.getCode());
    }
}
