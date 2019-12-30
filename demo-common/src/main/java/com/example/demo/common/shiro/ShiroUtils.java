package com.example.demo.common.shiro;

import com.example.demo.common.constant.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName ShiroUtils
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-30 15:09
 * @Version 1.0
 */
public class ShiroUtils {
    /**
     * 私有构造器
     **/
    private ShiroUtils() {
    }

    /**
     * 获得 Subject
     *
     * @return Subject class
     * @author wuhengzhen
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * @description 获取当前用户Session
     * @author wuhengzhen
     * @date 2019/12/30 15:15
     **/
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }


    /**
     * @description 获取当前用户信息
     * @author wuhengzhen
     * @date 2019/12/30 15:22
     **/
    public static ShiroUser getUserInfo() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 判断是否登录
     *
     * @return boolean
     * @author wuhengzhen
     */
    public static boolean isLogin() {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isAuthenticated();
    }

    /**
     * 判断是否是锁定状态
     *
     * @param
     * @return
     */
    public static boolean isLocked(String enabled) {
        if (enabled.equals(Constant.USER_STATUS_LOCKED) || enabled.equals(Constant.USER_STATUS_UNACTIVATED_LOCKED)) {
            return true;
        }
        return false;
    }


    /**
     * @description 用户登出
     * @author wuhengzhen
     * @date 2019/12/30 15:16
     **/
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}
