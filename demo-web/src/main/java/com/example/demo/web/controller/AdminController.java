package com.example.demo.web.controller;

import com.example.demo.common.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description 管理员
 * @Author wuhengzhen
 * @Date 2019-12-16 16:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Result<String> getMessage() {
        return Result.wrapSuccessfulResult("您拥有管理员权限，可以获得该接口的信息！", null);
    }
}
