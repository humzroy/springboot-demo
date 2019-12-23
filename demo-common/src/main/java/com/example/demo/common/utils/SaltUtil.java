package com.example.demo.common.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author wuhengzhen
 * @Description：加盐工具
 * @date：2018-09-20 11:37
 */
public class SaltUtil {

    public static final String RA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    /**
     * 盐的长度，此处设为8
     */
    private static final Integer SALT_LENGTH = 16;

    /**
     * 密码进行加盐
     * <p>
     * 加盐规则：
     * 1.随机生成十六位的字符
     * 2.字符串加盐：密码和随机生成的十六位字符串组成密码加盐字符串
     * 2.密码加盐字符串生成的十六进制的MD5
     * 3.MD5加盐：new一个48位的char数组，然后往这个48位数组中插入32位的密码加盐字符串生成的十六进制的MD5和随机生成的16位字符串；
     * 插入规则为把48位数组分成16块，每块的第一和第三个位置按顺序放入密码加盐字符串生成的十六进制的MD5的值，第二个位置按顺序放入随机字符
     * 4.返回加盐后的密码
     * </p>
     *
     * @param pwd 要进行加盐的字符串
     * @return 经过加盐之后的一串字符
     */
    public static String addSalt(String pwd) throws NoSuchAlgorithmException {
        // 随机生成十六位的字符
        String salt = StringUtil.getStringRandom(16);

        //生成加盐密码字符串
        StringBuffer passSalt = new StringBuffer();
        passSalt.append(pwd).append(salt);

        //生成加盐密码字符串的十六进制MD5摘要
        String md5Hex = MD5Util.md5Hex(passSalt.toString());

        //加盐密码的MD5进行加盐
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = md5Hex.charAt(i / 3 * 2);
            cs[i + 1] = salt.charAt(i / 3);
            cs[i + 2] = md5Hex.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 校验密码和加盐的密码是否匹配
     *
     * @param pwd     密码
     * @param md5Salt 加盐之后的密码
     * @return 是否匹配
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifyPwd(String pwd, String md5Salt) throws NoSuchAlgorithmException {
        //加盐密码的md5
        char[] pwdSaltMd5 = new char[32];

        //加盐的char数组
        char[] saltChar = new char[16];

        for (int i = 0; i < 48; i += 3) {
            pwdSaltMd5[i / 3 * 2] = md5Salt.charAt(i);
            pwdSaltMd5[i / 3 * 2 + 1] = md5Salt.charAt(i + 2);
            saltChar[i / 3] = md5Salt.charAt(i + 1);
        }

        String salt = new String(saltChar);
        StringBuffer sb = new StringBuffer();
        sb.append(pwd).append(salt);
        return MD5Util.md5Hex(sb.toString()).equals(new String(pwdSaltMd5));
    }

    /**
     * 生成随机盐（第二种方式）
     *
     * @return 随机生成的盐
     */
    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        // 盐的长度，这里是16，可自行调整(8-16)
        char[] text = new char[SALT_LENGTH];
        for (int i = 0; i < SALT_LENGTH; i++) {
            text[i] = RA.charAt(random.nextInt(RA.length()));
        }
        return new String(text);
    }

}
