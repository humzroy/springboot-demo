package com.example.demo.common.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName AesUtils
 * @Description Aes加解密工具
 * @Author wuhengzhen
 * @Date 2019-11-06 13:27
 */
public class AesUtils {

    /**
     * @description 加密
     * @author wuhengzhen
     * @date 2019/11/6 13:32
     **/
    public static String aesEncrypt(String str, String aesKey) {
        try {
            SecretKey key = generateMySqlAesKey(aesKey, "ASCII");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = str.getBytes(StandardCharsets.UTF_8);
            byte[] cipherTextBytes = cipher.doFinal(cleartext);
            return new String(Hex.encodeHex(cipherTextBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 解密
     * @author wuhengzhen
     * @date 2019/11/6 13:32
     **/
    public static String aesDecrypt(String content, String aesKey) {
        try {
            SecretKey key = generateMySqlAesKey(aesKey, "ASCII");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cleartext = Hex.decodeHex(content.toCharArray());
            byte[] cipherTextBytes = cipher.doFinal(cleartext);
            return new String(cipherTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKeySpec generateMySqlAesKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes(encoding)) {
                finalKey[i++ % 16] ^= b;
            }
            return new SecretKeySpec(finalKey, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {
        String abc = "张三";
        String key = "test";
        String result = aesEncrypt(abc, key);
        System.out.println(result);
        System.out.println(aesDecrypt(result, key));
    }
}
