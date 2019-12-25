package com.example.demo.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author : wuhengzhen
 * @Description : UUID工具类
 * @date : 2018/09/07 16:54
 * @system name:
 * @copyright:
 */
public class IdUtils {

    private static final String ALL_CHAR_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHAR = "0123456789";
    private static final String SPECIAL_CHAR = "!@#$%^&*()_+";

    /**
     * @description :获得一个小写UUID 32位 例如：06a5f6f79bdc4ff58f1841080b93dc66
     * @author : wuhengzhen
     * @date : 2018/3/22 16:18
     */
    public static String getLowerCaseUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得一个大写的UUID 32位
     * 例如：8BEB3F6132BC43BC85A6F7D20E9CCD3F
     *
     * @return
     */
    public static String getUppercaseUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    /**
     * 生成32位随机数UNID（16位本机标识码+8位时间戳+8位随机数）
     * 例如：0000AAFD7A79BE86B1D6B47752909B8E
     *
     * @return String UNID标识符
     */
    public String getUNID() {
        StringBuilder buf = new StringBuilder();
        SecureRandom seeder = new SecureRandom();
        long time = System.currentTimeMillis();
        // 生成当前时间戳
        int timeLow = (int) time;
        // 生成随机数
        int node = seeder.nextInt();
        String midString = null;
        try {
            InetAddress inet = InetAddress.getLocalHost();
            byte[] bytes = inet.getAddress();
            String hexAddress = hexFormat(getInt(bytes), 8);
            String hash = hexFormat(System.identityHashCode(this), 8);
            // 本机标识码
            midString = hexAddress + hash;
        } catch (UnknownHostException ignored) {

        }
        if (midString == null) {
            midString = "0000000000000000";
        }

        buf.append(midString).append(hexFormat(timeLow, 8)).append(hexFormat(node, 8));
        return buf.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateInteger(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateAllString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR_STR.charAt(random.nextInt(ALL_CHAR_STR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        return getFixdLengthString(String.valueOf(num), fixdlenth);
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        return getFixdLengthString(String.valueOf(num), fixdlenth);
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     * @param fixdlenth
     * @return
     */
    private static String getFixdLengthString(String num, int fixdlenth) {
        StringBuilder sb = new StringBuilder();
        if (fixdlenth - num.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - num.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(num);
        return sb.toString();
    }

    /**
     * 八位数字+字母+特殊字符随机密码生成
     *
     * @return
     */
    public static String generatePwdStr() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        for (int i = 0; i < 2; i++) {
            sb.append(SPECIAL_CHAR.charAt(random.nextInt(SPECIAL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 转成16进制格式
     *
     * @param number
     * @param digits
     * @return
     */
    private static String hexFormat(int number, int digits) {
        String hex = Integer.toHexString(number).toUpperCase();
        if (hex.length() >= digits) {
            return hex.substring(0, digits);
        } else {
            int padding = digits - hex.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < padding; ++i) {
                sb.append("0");
            }
            sb.append(hex);
            return sb.toString();
        }
    }

    /**
     * @param bytes
     * @return
     */
    private int getInt(byte[] bytes) {
        int size = (bytes.length > 32) ? 32 : bytes.length;
        int result = 0;
        for (int i = size - 1; i >= 0; i--) {
            if (i == (size - 1)) {
                result += bytes[i];
            } else {
                result += (bytes[i] << 4 * (size - 1 - i));
            }
        }
        return result;
    }

}
