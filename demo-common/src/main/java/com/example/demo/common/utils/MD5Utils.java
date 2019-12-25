package com.example.demo.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : wuhengzhen
 * @Description : MD5工具类
 * @date : 2018/09/14 11:51
 * @system name:
 * @copyright:
 */
public class MD5Utils {
    /**
     * LOG
     */
    private static Log log = LogFactory.getLog(MD5Utils.class);

    /**
     * 适用于上G大的文件
     *
     * @param file
     * @return 加密的密文
     * @throws IOException
     */
    public static String encrypt(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(byteBuffer);
            return DataFormatUtils.byte2hex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("初始化失败，MessageDigest不支持MD5Utils，原因是：" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 对字符串进行普通md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行普通md5加密(大写+数字)
     *
     * @param s 传入要加密的字符串
     * @return MD5加密后的字符串
     */

    public static String md5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加盐MD5加密
     *
     * @param str  明文密码
     * @param salt 盐值
     * @return MD5加密后的密文
     */
    public static String md5Salt(String str, String salt) {
        String md5Str;
        try {
            // MD5加密
            md5Str = md5(str + salt);
            return md5Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 密码校验（普通MD5）
     *
     * @param password  明文密码
     * @param md5PwdStr 密文
     * @return 相等：true，不相等：false
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String md5Str = md5(password);
        if (StringUtils.isNotEmpty(md5Str)) {
            return md5Str.equals(md5PwdStr);
        } else {
            return false;
        }
    }

    /**
     * 密码校验（加盐MD5）
     *
     * @param password  明文密码
     * @param salt      盐
     * @param md5PwdStr 密文
     * @return 相等：true，不相等：false
     */
    public static boolean checkPassword(String password, String salt, String md5PwdStr) {
        String md5Str = md5(password + salt);
        if (StringUtils.isNotEmpty(md5Str)) {
            return md5Str.equals(md5PwdStr);
        } else {
            return false;
        }
    }

    /**
     * 获取字节数组形式的MD5摘要
     *
     * @param data 需要获取摘要的字节数组
     * @return 获取到的MD5摘要数组
     * @throws Exception
     */
    public static byte[] md5Byte(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     *
     * @param src 需要获得MD5摘要的字符串
     * @return 获取到的十六进制的MD5摘要
     */
    public static String md5Hex(String src) throws NoSuchAlgorithmException {
        byte[] bs = md5Byte(src.getBytes());
        return new String(new Hex().encode(bs));
    }

}

