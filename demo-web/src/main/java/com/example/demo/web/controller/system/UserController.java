package com.example.demo.web.controller.system;

import com.example.demo.biz.service.system.IUserService;
import com.example.demo.common.entity.Result;
import com.example.demo.dao.entity.system.User;
import com.example.demo.web.framework.annotction.PhoneNumber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 13:31
 * @Version 1.0
 */
@Api(description = "UserController")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("根据手机号查询用户信息")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query")
    @GetMapping("/query/userInfo")
    public Result queryUserInfo(@Validated @PhoneNumber String phone) {
        return userService.queryUserInfo(phone);
    }

    @ApiOperation("修改用户信息")
    @ApiImplicitParam(name = "user", value = "用户信息json", required = true, paramType = "update")
    @PostMapping("/update/userInfo")
    public Result updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }


}
