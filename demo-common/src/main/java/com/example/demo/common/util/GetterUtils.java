package com.example.demo.common.util;

import cn.hutool.core.lang.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author wuhengzhen
 * @date 2021/01/27 09:52
 **/

public class GetterUtils {
    /**
     * 取得String
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        if (Validator.isNull(obj)) {
            return "";
        }
        return String.valueOf(obj);
    }

    /**
     * 取得String
     *
     * @param obj
     * @param val 默认值
     * @return
     */
    public static String getString(Object obj, String val) {
        if (Validator.isNull(obj)) {
            return val;
        }
        return String.valueOf(obj);
    }

    /**
     * 取得double
     *
     * @param obj
     * @return
     */
    public static double getDouble(Object obj) {
        if (Validator.isNull(obj)) {
            return 0.0;
        }
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 取得long
     *
     * @param obj
     * @return
     */
    public static long getLong(Object obj) {
        if (Validator.isNull(obj)) {
            return 0;
        }

        try {
            return Long.parseLong(String.valueOf(obj));
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 取得int
     *
     * @param obj
     * @return
     */
    public static int getInteger(Object obj) {
        if (Validator.isNull(obj)) {
            return 0;
        }

        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 取得int
     *
     * @param obj
     * @return
     */
    public static int getInteger(Object obj, int defaultValue) {
        if (Validator.isNull(obj)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 取得BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal getBigDecimal(Object obj, int scale) {
        if (Validator.isNull(obj)) {
            return new BigDecimal(0);
        }

        try {
            return new BigDecimal(obj.toString()).setScale(scale, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }
}