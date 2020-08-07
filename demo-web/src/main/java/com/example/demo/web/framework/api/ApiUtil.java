package com.example.demo.web.framework.api;

import com.example.demo.common.entity.api.NotRepeatSubmit;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ApiUtil
 * @Description
 * @Author wuhengzhen
 * @Date 2020-08-07 16:04
 * @Version 1.0
 */
public class ApiUtil {
    /**
     * 按参数名升续拼接参数
     *
     * @param request
     * @return
     */
    public static String concatSignString(HttpServletRequest request) {
        Map<String, String> paramterMap = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> paramterMap.put(key, value[0]));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 或略掉的字段
            if (k.equals("sign")) {
                continue;
            }
            if (paramterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    public static String concatSignString(Map<String, String> map) {
        Map<String, String> paramterMap = new HashMap<>();
        map.forEach((key, value) -> paramterMap.put(key, value));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (paramterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 获取方法上的@NotRepeatSubmit注解
     *
     * @param handler
     * @return
     */
    public static NotRepeatSubmit getNotRepeatSubmit(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            NotRepeatSubmit annotation = method.getAnnotation(NotRepeatSubmit.class);
            return annotation;
        }
        return null;
    }
}