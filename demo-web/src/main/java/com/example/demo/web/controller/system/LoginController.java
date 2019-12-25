package com.example.demo.web.controller.system;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.biz.service.system.ILoginService;
import com.example.demo.common.entity.Result;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.web.framework.annotction.PhoneNumber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName LoginController
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 13:31
 * @Version 1.0
 */
@Api(description = "登录接口")
@RestController
@Validated
public class LoginController {

    @Resource
    private ILoginService loginService;

    @ApiOperation("发送登录验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query")
    @GetMapping("/loginCode")
    public Result sendLoginCode(@Valid @PhoneNumber String phone) throws ClientException {
        return loginService.sendLoginCode(phone);
    }


    @ApiOperation("密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query")
    })
    @PostMapping("/login/password")
    public Result loginByPassword(@Valid @PhoneNumber String phone, @NotEmpty(message = "密码不能为空") String password) {
        return loginService.loginByPassword(phone, password);
    }


    @ApiOperation("验证码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query")
    })
    @PostMapping("/login/code")
    public Result loginByCode(@Valid @PhoneNumber String phone, @NotEmpty(message = "验证码不能为空") String code) {
        return loginService.loginByCode(phone, code);
    }


    /**
     * Unauthorized api result.
     *
     * @param message the message
     * @return the api result
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @ApiOperation(value = "无权限", notes = "无权限跳转的接口")
    @GetMapping(path = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return Result.wrapErrorResult(ErrorCodes.NOT_AUTH.getCode(), message);
    }
}
