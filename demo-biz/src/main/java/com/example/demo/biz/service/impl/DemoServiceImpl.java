package com.example.demo.biz.service.impl;

import com.example.demo.biz.exception.BizException;
import com.example.demo.biz.service.DemoService;
import com.example.demo.biz.service.UserService;
import com.example.demo.common.error.DemoErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoServiceImpl
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 10:16
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private UserService userService;

    /**
     * @description 接口测试
     * @author wuhengzhen
     * @date 2019/11/6 10:15
     **/
    @Override
    public String test(Integer id) {
        return userService.getUserStr(id);
    }

    /**
     * @description 测试事务
     * @author wuhengzhen
     * @date 2019/11/6 15:21
     **/
    @Override
    public void testTransaction() {
        innerMethod();
    }

    @Transactional(rollbackFor = Exception.class)
    private void innerMethod() {
        userService.addUser("张三");
        userService.addUser("李四");
        throw new BizException(DemoErrors.SYSTEM_ERROR);
    }
}
