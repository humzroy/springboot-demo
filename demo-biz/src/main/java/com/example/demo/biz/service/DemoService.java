package com.example.demo.biz.service;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 10:14
 */
public interface DemoService {
    /**
     * @description 接口测试
     * @author wuhengzhen
     * @date 2019/11/6 10:15
     **/
    String test(Integer id);

    /**
     * @description 测试事务
     * @author wuhengzhen
     * @date 2019/11/6 15:21
     **/
    void testTransaction();
}
