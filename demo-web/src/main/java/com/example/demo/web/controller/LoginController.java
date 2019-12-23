package com.example.demo.web.controller;

import com.example.demo.biz.service.UserService;
import com.example.demo.common.entity.Result;
import com.example.demo.common.error.DemoErrors;
import com.example.demo.common.utils.ExceptionUtil;
import com.example.demo.dao.entity.system.SUser;
import com.example.demo.web.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    /**
     * 用户名密码登录
     *
     * @param request
     * @return token
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody SUser loginInfo, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        try {
            // shiro 登录验证
            UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getLoginName(), loginInfo.getPassword());
            subject.login(token);

            ShiroUser user = (ShiroUser) subject.getPrincipal();
            // 生成token返回
            String newToken = userService.generateJwtToken(user.getUserCode());
            response.setHeader("x-auth-token", newToken);

            return Result.wrapSuccessfulResult("登录成功！", newToken);
        } catch (AuthenticationException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("User {} login fail, Reason:{}", loginInfo.getLoginName(), e.getMessage());
            return Result.wrapErrorResult(DemoErrors.USER_UNAUTHORIZED.getCode(), e.getMessage());
        } catch (Exception e) {
            return Result.wrapErrorResult(DemoErrors.SYSTEM_ERROR.getCode(), DemoErrors.SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            SUser user = (SUser) subject.getPrincipals().getPrimaryPrincipal();
            userService.deleteLoginInfo(user.getLoginName());
        }
        SecurityUtils.getSubject().logout();
        return Result.wrapSuccessfulResult("退出登录成功！", null);
    }


    /**
     * 新用户注册
     *
     * @param user     用户注册信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(path = "register")
    public Result<SUser> registerUser(@RequestBody SUser user, HttpServletRequest request, HttpServletResponse response) {
        Result<SUser> result = new Result<>();
        try {
            int row = userService.registerUser(user);
            if (row == -1) {
                logger.error("该用户名已存在！");
                return Result.wrapErrorResult(DemoErrors.USER_ALREADY_EXISTS.getCode(), DemoErrors.USER_ALREADY_EXISTS.getMessage());
            } else if (row != 1) {
                logger.error("注册失败！");
                return Result.wrapErrorResult(DemoErrors.USER_REGISTER_FAILD.getCode(), DemoErrors.USER_REGISTER_FAILD.getMessage());

            }
        } catch (Exception e) {
            logger.error("注册异常！异常信息：{}", e.getMessage());
            return Result.wrapErrorResult(DemoErrors.SYSTEM_ERROR.getCode(), "注册新用户异常！");
        }
        return Result.wrapSuccessfulResult("注册成功！", user);
    }

    /**
     * Unauthorized api result.
     *
     * @param message the message
     * @return the api result
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @GetMapping(path = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return Result.wrapErrorResult(DemoErrors.USER_UNAUTHORIZED.getCode(), message);
    }

}
