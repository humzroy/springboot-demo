package com.example.demo.web.controller;

import com.example.demo.biz.service.UserService;
import com.example.demo.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // @PostMapping("add")
    // @ApiOperation("添加用户")
    // public void addUser(
    //         @ApiParam(name = "userNick", value = "用户昵称", example = "test", required = true)
    //         @RequestParam("userNick") String userNick) {
    //     userService.addUser(userNick);
    // }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Result<String> getMessage() {
        return Result.wrapSuccessfulResult("您拥有用户权限，可以获得该接口的信息！", null);
    }

}
