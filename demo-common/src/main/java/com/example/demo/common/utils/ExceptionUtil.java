package com.example.demo.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author : wuhengzhen
 * @Description : 异常工具类
 * @date : 2018/09/11 14:05
 * @system name:
 * @copyright:
 */
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     *
     * @param t 异常
     * @return String
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    public static RuntimeException unchecked(Exception e) {
        if ((e instanceof RuntimeException)) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

    public static boolean isCausedBy(Exception ex, Class<? extends Exception>[] causeExceptionClasses) {
        Throwable cause = ex;
        while (cause != null) {
            for (Class causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
