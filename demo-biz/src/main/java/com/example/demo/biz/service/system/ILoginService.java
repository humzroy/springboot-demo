package com.example.demo.biz.service.system;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.entity.Result;
import com.example.demo.dao.entity.system.User;

/**
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 15:16
 */
public interface ILoginService extends IService<User> {

    /**
     * 根据手机号查询用户
     *
     * @param phone phone
     * @return User
     */
    default User selectUserByPhone(String phone) {
        return this.getOne(new QueryWrapper<User>().eq("phone", phone));
    }


    /**
     * 发送注册验证码
     *
     * @param phone phone
     * @return 发送是否成功
     * @throws ClientException 阿里云短信异常
     */
    Result sendLoginCode(String phone) throws ClientException;

    /**
     * 密码登录方式
     *
     * @param phone    phone
     * @param password password
     * @return Result
     */
    Result loginByPassword(String phone, String password);


    /**
     * 验证码登录方式
     *
     * @param phone phone
     * @param code  code
     * @return Result
     */
    Result loginByCode(String phone, String code);

}
