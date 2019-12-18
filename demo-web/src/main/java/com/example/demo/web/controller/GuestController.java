package com.example.demo.web.controller;

import com.example.demo.common.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GuestController
 * @Description 游客
 * @Author wuhengzhen
 * @Date 2019-12-16 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/guest")
public class GuestController {
    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public Result<String> login() {
        return Result.wrapSuccessfulResult("欢迎进入，您的身份是游客", null);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Result<String> submitLogin() {
        return Result.wrapSuccessfulResult("您拥有获得该接口的信息的权限", null);
    }
}
