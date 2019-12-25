package com.example.demo.biz.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.biz.service.system.IUserService;
import com.example.demo.common.entity.Result;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.dao.entity.system.User;
import com.example.demo.dao.mapper.business.system.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    /**
     * @param phone
     * @description 根据手机号查询用户信息
     * @author wuhengzhen
     * @date 2019/12/25 16:01
     */
    @Override
    public Result queryUserInfo(String phone) {
        User user = this.selectUserByPhone(phone);
        return Result.wrapSuccessfulResult(user);
    }

    /**
     * @param user
     * @description 修改用户信息
     * @author wuhengzhen
     * @date 2019/12/25 16:32
     */
    @Override
    public Result updateUserInfo(User user) {
        boolean flag = this.updateById(user);
        if (flag) {
            return Result.wrapSuccessfulResult("修改成功", null);
        } else {
            return Result.wrapErrorResult(ErrorCodes.SYSTEM_ERROR, "修改失败");

        }
    }
}
