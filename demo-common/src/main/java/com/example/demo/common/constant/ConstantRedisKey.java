package com.example.demo.common.constant;

/**
 * @author wuhengzhen
 * @date 2019/10/26 12:03
 */
public class ConstantRedisKey {

    public static String getRegisterCodeKey(String phone) {
        return "REGISTER:CODE:" + phone;
    }

    public static String getLoginCodeKey(String phone) {
        return "LOGIN:CODE:" + phone;
    }

}
