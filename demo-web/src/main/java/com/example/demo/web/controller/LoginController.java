package com.example.demo.web.controller;

import com.example.demo.biz.service.UserService;
import com.example.demo.common.entity.Result;
import com.example.demo.common.utils.JWTUtil;
import com.example.demo.dao.entity.shiro.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // @PostMapping(value = "/login")
    // public ResponseEntity<Void> login(@RequestBody User loginInfo, HttpServletRequest request, HttpServletResponse response) {
    //     Subject subject = SecurityUtils.getSubject();
    //     try {
    //         UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getName(), loginInfo.getPassword());
    //         subject.login(token);
    //
    //         User user = (User) subject.getPrincipal();
    //         String newToken = userService.generateJwtToken(user.getName());
    //         response.setHeader("x-auth-token", newToken);
    //
    //         return ResponseEntity.ok().build();
    //     } catch (AuthenticationException e) {
    //         logger.error("User {} login fail, Reason:{}", loginInfo.getName(), e.getMessage());
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    @PostMapping(value = "/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        String realPassword = userService.getPassword(username);
        if (realPassword == null) {
            return Result.wrapErrorResult(401, "用户名错误!");
        } else if (!realPassword.equals(password)){
            return Result.wrapErrorResult(401, "密码错误!");
        } else {
            return Result.wrapSuccessfulResult("登录成功!", JWTUtil.sign(username, "123456abc"));
        }

    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            User user = (User) subject.getPrincipals().getPrimaryPrincipal();
            userService.deleteLoginInfo(user.getName());
        }
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
    }

}
