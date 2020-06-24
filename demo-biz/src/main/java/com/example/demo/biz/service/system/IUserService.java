package com.example.demo.biz.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.datasource.DataSource;
import com.example.demo.common.datasource.DataSourceConstant;
import com.example.demo.common.entity.Result;
import com.example.demo.dao.entity.system.User;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 15:22
 */
public interface IUserService extends IService<User> {

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
     * @description 根据手机号查询用户信息
     * @author wuhengzhen
     * @date 2019/12/25 16:01
     **/
    Result queryUserInfo(String phone);

    /**
     * @description 修改用户信息
     * @author wuhengzhen
     * @date 2019/12/25 16:52
     **/
    Result updateUserInfo(User user);
}
