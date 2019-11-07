package com.example.demo.web.controller;

import com.example.demo.biz.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName UserController
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 15:27
 */
@Api(tags = "用户")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    @ApiOperation("添加用户")
    public void addUser(
            @ApiParam(name = "userNick", value = "用户昵称", example = "test", required = true)
            @RequestParam("userNick") String userNick) {
        userService.addUser(userNick);
    }


}
