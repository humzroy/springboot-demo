package com.example.demo.biz.service.impl;

import com.example.demo.common.exception.BizException;
import com.example.demo.biz.service.DemoService;
import com.example.demo.biz.service.system.IUserService;
import com.example.demo.common.error.ErrorCodes;
import com.example.demo.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private IUserService userService;

    /**
     * @description 接口测试
     * @author wuhengzhen
     * @date 2019/11/6 10:15
     **/
    @Override
    public String test(Integer id) {
        // return userService.getUserStr(id);
        return "";
    }

    /**
     * @description 测试事务
     * @author wuhengzhen
     * @date 2019/11/6 15:21
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testTransaction() {
        innerMethod();
    }

    /**
     * @description 测试http
     * @author wuhengzhen
     * @date 2019/11/7 16:22
     **/
    @Override
    public void testHttp() {
        long start = System.currentTimeMillis();
        String json = HttpUtils.sendGet("http://www.baidu.com", "");
        log.info("耗时:{}", System.currentTimeMillis() - start);
        log.info(json);
    }


    private void innerMethod() {
        // userService.addUser("张三");
        // userService.addUser("李四");
        throw new BizException(ErrorCodes.SYSTEM_ERROR);
    }
}
