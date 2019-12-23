package com.example.demo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据格式工具类
 */
public class DataFormatUtil {
    /**
     * 字节转2位16进制字窜
     *
     * @param b
     * @return
     */
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    private static final DecimalFormat WEST_NUM_FORMAT = new DecimalFormat("#,###.00");

    private static final NumberFormat PERCENTAGE_FORMAT = NumberFormat.getPercentInstance();

    private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    public DataFormatUtil() {
    }

    /**
     * short 转 byte 数组
     *
     * @param s int
     * @return 4位 byte 数组
     * 数组0位存储int最高位信息，3位存贮最低位信息
     */
    public static byte[] short2byte(short s) {
        byte[] ba = new byte[2];
        ba[0] = (byte) (s >> 8);
        ba[1] = (byte) s;
        return ba;
    }

    /**
     * unsigned int 转 byte 数组
     *
     * @param n int
     * @return 4位 byte 数组
     * 数组0位存储int最高位信息，3位存贮最低位信息
     */
    public static byte[] unsignedInt2byte(long n) {
        byte[] ba = new byte[4];
        ba[0] = (byte) ((n & 0xFF000000L) >> 24);
        ba[1] = (byte) ((n & 0x00FF0000L) >> 16);
        ba[2] = (byte) ((n & 0x0000FF00L) >> 8);
        ba[3] = (byte) (n & 0x000000FFL);
        return ba;
    }

    /**
     * int 转 byte 数组
     *
     * @param n int
     * @return 4位 byte 数组
     * 数组0位存储int最高位信息，3位存贮最低位信息
     */
    public static byte[] int2byte(int n) {
        byte[] ba = new byte[4];
        ba[0] = (byte) (n >> 24);
        ba[1] = (byte) (n >> 16);
        ba[2] = (byte) (n >> 8);
        ba[3] = (byte) n;
        return ba;
    }

    /**
     * byte 数组 转 unsigned int
     *
     * @param ba         byte 数组
     * @param beginIndex 从beginIndex开始读取后4位字节转化为int，不足4位的取到数组的末位
     * @return int 整数
     */
    public static long byte2UnsignedInt(byte[] ba, int beginIndex) {
        return byte2int(ba, beginIndex) & 0xFFFFFFFFL;
    }

    /**
     * byte 数组 转 int
     *
     * @param ba         byte 数组
     * @param beginIndex 从beginIndex开始读取后4位字节转化为int，不足4位的取到数组的末位
     * @return int 整数
     */
    public static int byte2int(byte[] ba, int beginIndex) {
        int n = 0;
        if (ba != null) {
            for (int i = beginIndex; (i < ba.length) && (i < beginIndex + 4); i++) {
                n = n << 8;
                n = n | (ba[i] & 0xff);
            }
        }
        return n;
    }

    /**
     * byte 数组 转 unsigned int
     *
     * @param ba byte 数组
     * @return int 整数
     */
    public static long byte2UnsignedInt(byte[] ba) {
        return byte2int(ba) & 0xFFFFFFFFL;
    }

    /**
     * byte 数组 转 int
     *
     * @param ba byte 数组
     * @return int 整数
     */
    public static int byte2int(byte[] ba) {
        int n = 0;
        if (ba != null) {
            for (int i = 0; (i < ba.length) && (i < 4); i++) {
                n = n << 8;
                n = n | (ba[i] & 0xff);
            }
        }
        return n;
    }

    /**
     * long转为byte
     *
     * @param n long
     * @return 8位byte数组
     */
    public static byte[] long2byte(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (int) (n >> 56);
        b[1] = (byte) (int) (n >> 48);
        b[2] = (byte) (int) (n >> 40);
        b[3] = (byte) (int) (n >> 32);
        b[4] = (byte) (int) (n >> 24);
        b[5] = (byte) (int) (n >> 16);
        b[6] = (byte) (int) (n >> 8);
        b[7] = (byte) (int) n;
        return b;
    }

    /**
     * byte转为long
     *
     * @param b 8位byte数组
     * @return long
     */
    public static long byte2long(byte[] b) {
        return (long) b[7] & (long) 255 | ((long) b[6] & (long) 255) << 8 | ((long) b[5] & (long) 255) << 16 | ((long) b[4] & (long) 255) << 24 | ((long) b[3] & (long) 255) << 32
                | ((long) b[2] & (long) 255) << 40 | ((long) b[1] & (long) 255) << 48 | (long) b[0] << 56;
    }

    /**
     * byte转为unsigned long
     *
     * @param b 8位byte数组
     * @return
     */
    public static String byte2unsignedLong(byte[] b) {
        //由于java中的long为signedLong，所以需要通过将最高位的进行剥离，进行特殊处理
        //最高位为1时unsignedLong为9223372036854775808，需要拆分成"9"+"223372036854775808"
        StringBuffer unsignedLong = new StringBuffer();
        long signedLong = byte2long(b);

        //小于0表示高位为1，需要特殊处理
        if (signedLong < 0) {
            //1、取模
            long newSignedLong = signedLong & 0x7FFFFFFFFFFFFFFFL;

            //2、添加拆分信息
            String sNewSignedLong = String.valueOf(newSignedLong + 223372036854775808L);

            //3、添加高位拆分"9"计算
            if (sNewSignedLong.length() > 18) {
                int top = (9 + Integer.parseInt(sNewSignedLong.substring(0, 1)));
                unsignedLong = unsignedLong.append(top).append(sNewSignedLong.substring(1));
            } else {
                unsignedLong = unsignedLong.append(9).append(sNewSignedLong);
            }
        } else {
            unsignedLong = unsignedLong.append(signedLong);
        }
        return unsignedLong.toString();
    }

    /**
     * 将时间戳转成字符
     *
     * @param format 时间的标示格式
     * @return 时间戳字符串
     */
    public static String getTimeStamp(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sTimeStamp = sdf.format(date);
        return sTimeStamp;
    }

    /**
     * 将BCD编码的一个字节转化成对应的字符窜
     *
     * @param b BCD编码的字节
     * @return 2位的正数字符窜，如08 、13
     */
    public static String bcd2String(byte b) {
        byte low = (byte) (b & 0x0f);
        byte high = (byte) ((b >> 4) & 0x0f);
        return String.valueOf(high * 10 + low);
    }

    /**
     * b -> 16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 字节码转换成16进制字符串
     *
     * @param b
     * @return String
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
            if (n < b.length - 1) {
                hs.append(stmp);
            }
        }
        return hs.toString().toLowerCase();
    }

    /**
     * @Description :利用FastJson将Object转变为Map
     * @author: wuhengzhen
     * @date: 2018/1/5 11:14
     */
    public static Map<String, Object> object2Map(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * @param instr 输入驼峰式字符串
     * @return String 返回数据库字段名
     * @Description :将驼峰命名转为数据库字段名
     * @author: wuhengzhen
     * @date: 2017/11/6 17:22
     */
    public static String convertDump2Sql(String instr) {

        List<String> strList = Arrays.asList(instr.split(""));
        List<String> arrayList = new ArrayList(strList);
        for (int i = 0; i < arrayList.size(); i++) {
            char x = arrayList.get(i).charAt(0);
            if (x <= 90 && x >= 65) {
                x += 32;
                arrayList.add(i, "_");
                i++;
            }
        }
        return String.join("", arrayList);
    }

    /**
     * 将输入数字转为String类型的带逗号的格式
     *
     * @param input 输入的BigDecimal
     * @return String
     */
    public static String convertNum2WestFormatStr(BigDecimal input) {
        if (input == null) {
            return null;
        }
        String x = WEST_NUM_FORMAT.format(input);
        // 0 ~ 1 之间
        boolean flag = (input.compareTo(BigDecimal.ZERO) == 1 && input.compareTo(BigDecimal.ONE) == -1);
        if (flag || input.compareTo(BigDecimal.ZERO) == 0) {
            return "0" + x;
        }
        // 0 ~ -1 之间
        if (input.compareTo(BigDecimal.ZERO) == -1 && input.abs().compareTo(BigDecimal.ONE) == -1) {
            return "-0" + x.substring(1);
        }
        return x;
    }

    /**
     * 将小数转换指定位数为百分数
     *
     * @param input 输入小数
     * @param num   指定小数位数
     * @return String
     */
    public static String convertDecimal2Percentage(BigDecimal input, int num) {
        if (input == null) {
            return null;
        }
        PERCENTAGE_FORMAT.setMaximumFractionDigits(num);
        return PERCENTAGE_FORMAT.format(input);
    }

    /**
     * 将人民币小写转换成为大写
     *
     * @param v 需要转换的小写金额
     * @return String
     * @author zhangchengjian
     */
    public static String convertLower2Upper(double v) {
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        StringBuilder rs = new StringBuilder();
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万'
                        || UNIT.charAt(j) == '元') {
                    rs.append(UNIT.charAt(j));
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs.append("零");
                    isZero = false;
                }
                rs.append(DIGIT.charAt(ch - '0')).append(UNIT.charAt(j));
            }
        }
        rs = new StringBuilder(rs.toString().replaceAll("亿万", "亿"));
        return rs.toString();
    }
}
