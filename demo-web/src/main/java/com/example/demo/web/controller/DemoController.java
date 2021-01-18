package com.example.demo.web.controller;

import com.example.demo.common.entity.Result;
import com.example.demo.web.framework.api.RateLimit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "查询产品详情")
    @GetMapping(value = "product/{productId}")
    @RateLimit(limitKey = "5")
    public Result queryProduct(@PathVariable("productId") Integer productId) {
        System.out.println(productId);
        return Result.wrapSuccessfulResult("查询成功");
    }
}
