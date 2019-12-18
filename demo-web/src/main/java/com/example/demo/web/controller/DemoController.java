package com.example.demo.web.controller;

import com.example.demo.biz.service.DemoService;
import com.example.demo.common.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoController
 * @Description Demo
 * @Author wuhengzhen
 * @Date 2019-11-06 10:05
 */
@RestController
@RequestMapping("demo")
public class DemoController {


    @Autowired
    private DemoService demoService;

    @GetMapping("test")
    @ApiOperation("测试")
    public Result<String> test(@RequestParam("id") Integer id) {
        return Result.wrapSuccessfulResult(demoService.test(id));
    }

    @GetMapping("test/transaction")
    @ApiOperation("测试事务")
    public void testTransaction() {
        demoService.testTransaction();
    }

    @PostMapping("http")
    @ApiOperation("测试http")
    public Result<String> testHttp() {
        demoService.testHttp();
        return Result.wrapSuccessfulResult("success");

    }
}
