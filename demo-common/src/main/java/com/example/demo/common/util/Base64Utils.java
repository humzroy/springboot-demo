package com.example.demo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description：BASE64 加密工具类
 * Author：wuhengzhen
 * Date：2018-09-20
 * Time：11:50
 */
public class Base64Utils {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }


    /**
     * 本地图片转换成base64字符串
     *
     * @param imgFile 图片本地路径
     * @return
     * @author wuhengzhen
     * @dateTime 2018-02-23 14:40:46
     */
    public static String imageToBase64byLocal(String imgFile) throws Exception {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return encryptBASE64(data);
    }


    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return
     * @author wuhengzhen
     * @dateTime 2018-02-23 14:43:18
     */
    public static String imageToBase64ByOnline(String imgURL) throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (is != null) {
                // 关闭流
                is.close();
            }
        }
        return encryptBASE64(data.toByteArray());
    }


    /**
     * base64字符串转换成图片
     *
     * @param imgStr      base64字符串
     * @param imgFilePath 图片存放路径
     * @return
     * @author wuhengzhen
     * @dateTime 2018-02-23 14:42:17
     */
    public static boolean base64ToImage(String imgStr, String imgFilePath) throws IOException {
        // 对字节数组字符串进行Base64解码并生成图片
        boolean flag = false;
        OutputStream out = null;
        // 图像数据不为空
        if (StringUtils.isNotEmpty(imgStr)) {
            try {
                // Base64解码
                byte[] b = decryptBASE64(imgStr);
                for (int i = 0; i < b.length; ++i) {
                    // 调整异常数据
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                flag = true;
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        return flag;
    }


    public static void main(String[] args) {
        /*String clearStr = "12yyyy34567890-=rtyuiodsfsdfsdfklmsfmsdflmsdafsdfsdfsdfwerrwerpteoryym/,ppdksdfldfsdlkmmsdf6783$%#!~!#$%^&*&(WUHENGZHENwuhengzhen22411";
        String clearMd5 = null;
        try {
            clearMd5 = MD5Util.encodeHex(clearStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String cipherStr = null;
        try {
            cipherStr = encryptBASE64(clearMd5.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("原文：" + clearStr);
        System.out.println("md5：" + clearMd5);
        System.out.println("base64：" + cipherStr.trim());
        System.out.println("base64长度：" + cipherStr.trim().length());*/

        // 本地图片地址
        String url = "E:/img/38.jpg";
        //在线图片地址
        String string = "http://bpic.588ku.com//element_origin_min_pic/17/03/03/7bf4480888f35addcf2ce942701c728a.jpg";
        String str;
        try {
            str = imageToBase64byLocal(url);
            String ste = imageToBase64ByOnline(string);
            System.out.println(str);

            base64ToImage(str, "E:/img/test1.jpg");

            base64ToImage(ste, "E:/img/test2.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
