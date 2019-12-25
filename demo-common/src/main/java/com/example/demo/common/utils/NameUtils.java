package com.example.demo.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @ClassName NameUtils
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 16:10
 * @Version 1.0
 */
@Slf4j
public class NameUtils {

    /**
     * @description 随机生成用户名（中文）
     * @author wuhengzhen
     * @date 2019/12/25 16:12
     **/
    public static String getRandomNickNameCN(int len) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str = null;
            // 定义高低位
            int hightPos, lowPos;
            Random random = new Random();
            // 获取高位值
            hightPos = (176 + Math.abs(random.nextInt(39)));
            // 获取低位值
            lowPos = (161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                // 转成中文
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret.append(str);
        }
        return ret.toString();
    }


}
