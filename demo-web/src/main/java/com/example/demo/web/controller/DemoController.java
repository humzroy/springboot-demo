package com.example.demo.web.controller;

import com.example.demo.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 15:25
 * @Version 1.0
 */
@Api(description = "Demo Controller")
@RestController
@RequestMapping("demo")
public class DemoController {

    @ApiOperation("hello word")
    @GetMapping("hello")
    public Result sayHello() {
        return Result.wrapSuccessfulResult("hello world!");
    }


}
