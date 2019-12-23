package com.example.demo.common.utils;

import com.google.common.collect.ImmutableMap;
import jodd.datetime.JDateTime;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @ClassName DateUtil
 * @Description 日期工具类, 通过静态继承的方式, 扩展common-lang3中的DateUtils
 * @Author wuhengzhen
 * @Date 2019-12-23 13:37
 * @Version 1.0
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    private static final Map<Pattern, String> PATTERN_MAP = ImmutableMap.<Pattern, String>builder()
            .put(Pattern.compile("^\\d{8}$"), "yyyyMMdd")
            .put(Pattern.compile("^\\d{14}$"), "yyyyMMddHHmmss")
            .put(Pattern.compile("^\\d{12}$"), "yyyyMMddHHmm")
            .put(Pattern.compile("^\\d{10}$"), "yyyyMMddHH")
            .put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$"), "y-M-d H:m:s.S")
            .put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$"), "y-M-d H:m:s")
            .put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}$"), "y-M-d H:m")
            .put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}$"), "y-M-d H")
            .put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$"), "y-M-d")
            .put(Pattern.compile("^\\d{2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$"), "H:m:s.S")
            .put(Pattern.compile("^\\d{2}:\\d{1,2}:\\d{1,2}$"), "H:m:s")
            .put(Pattern.compile("^\\d{2}:\\d{1,2}$"), "H:m")
            .build();

    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";


    /**
     * 自动解析日期/时间字符串, 此方法会自动识别日期格式.
     *
     * @param date 任意格式日期时间字符串, 具体参见{PATTERN_MAP}
     * @return 日期
     */
    public static Date autoParseDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        Date result = null;
        for (Pattern pattern : PATTERN_MAP.keySet()) {
            if (pattern.matcher(date).matches()) {
                String tmp = PATTERN_MAP.get(pattern);
                try {
                    result = parseDate(date, tmp);
                } catch (ParseException e) {
                    throw new RuntimeException("解析[" + tmp + "]格式的日期字符串[" + date + "]失败", e);
                }
            }
        }
        if (result == null) {
            throw new RuntimeException("无法识别日期字符串的格式: " + date);
        }
        return result;
    }


    /**
     * 将日期yyyy-MM-dd字符串解析为日期对象, 解析失败则返回null
     * 注意! 此方法会吞掉异常!
     *
     * @param date 日期字符串
     * @return 日期实例或null
     */
    public static Date parseDate(String date) {
        try {
            return parseDate(date, PATTERN_STANDARD10H);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将时间yyyy-MM-dd HH:mm:ss字符串解析为日期对象, 解析失败则返回null
     *
     * @param date 日期字符串
     * @return 日期实例或null
     */
    public static Date parseDateTime(String date) {
        Date result;
        try {
            result = parseDate(date, PATTERN_STANDARD19H);
        } catch (ParseException e) {
            result = null;
        }
        return result;
    }


    /**
     * 获取今天的日期, 不包括时间. 如yyyy-MM-dd 00:00:00.000
     *
     * @return yyyy-MM-dd 00:00:00.000
     */
    public static Date todayDate() {
        Calendar result = Calendar.getInstance();
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result.getTime();
    }


    /**
     * 根据开始日期和结束日期, 计算日期差.
     * 若日期字符串格式非法, 则返回-1
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 两个日期之间的天数
     */
    public static int getDays(String from, String to) {
        Date fromDate = parseDate(from);
        Date toDate = parseDate(to);
        int days;
        if (fromDate != null && toDate != null) {
            days = getDays(fromDate, toDate);
        } else {
            days = -1;
        }
        return days;
    }


    /**
     * 根据开始日期和结束日期, 计算日期差
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 两个日期之间的天数
     */
    public static int getDays(Date from, Date to) {
        int days = 0;
        if (from.before(to)) {
            while (from.before(to)) {
                days++;
                from = addDays(from, 1);
            }
        } else {
            days = -1;
        }
        return days;
    }


    /**
     * 获取时间段(闭区间)内的每一天, 日期均以yyyy-MM-dd格式字符串表示
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期段内的每一天, 若日期格式不合法则返回空结果
     */
    public static Set<String> getEveryDay(String start, String end) {
        Set<String> result = new TreeSet<String>();
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        if (startDate != null && endDate != null && startDate.getTime() <= endDate.getTime()) {
            Set<Date> allDate = getEveryDay(startDate, endDate);
            for (Date date : allDate) {
                result.add(format(date, PATTERN_STANDARD10H));
            }
        }
        return result;
    }


    /**
     * 获取时间段(闭区间)内的每一天
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期段内的每一天
     */
    public static Set<Date> getEveryDay(Date start, Date end) {
        Set<Date> result = new HashSet<Date>();
        while (start.getTime() <= end.getTime()) {
            result.add(start);
            start = addDays(start, 1);
        }
        return result;
    }


    /**
     * 在指定日期基础上增加day
     */
    public static String addDays(String date, int days) {
        Date tmp = parseDate(date);
        if (tmp == null) {
            return null;
        }
        tmp = addDays(tmp, days);
        return DateFormatUtils.format(tmp, PATTERN_STANDARD10H);
    }


    /**
     * 根据今天的时间 添加 numDay 天的时间
     *
     * @param numDay 要改变的 天数
     * @return
     */
    public static Date addDays(Date date, int numDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, numDay);
        Date tempDay = calendar.getTime();
        return tempDay;
    }


    /**
     * 根据今天的时间 添加 numDay 天的时间
     *
     * @param numDay 要改变的 天数
     * @return
     */
    public static Date addDays(int numDay) {
        // 取时间
        Date todayDate = DateUtil.todayDate();
        return addDays(todayDate, numDay);
    }


    /**
     * 将日期对象格式化为yyyy-MM-dd格式字符串
     *
     * @param date 日期对象
     * @return yyyy-MM-dd格式字符串
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, PATTERN_STANDARD10H);
    }


    /**
     * 将日期对象格式化为yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date 日期对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, PATTERN_STANDARD19H);
    }


    /**
     * 将日期格式化为指定格式字符串
     *
     * @param date    日期对象
     * @param pattern 字符串格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }


    /**
     * 获得当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        JDateTime jdt = new JDateTime();
        return jdt.toString("YYYY-MM-DD");
    }

    /**
     * description : 获取当前时间（格式：YYYY-MM-DD hh:mm:ss）
     * author : wuhengzhen
     * date : 2018-11-6 16:35
     */
    public static String getCurrentDateTime() {
        JDateTime jdt = new JDateTime();
        return jdt.toString("YYYY-MM-DD hh:mm:ss");
    }

    /**
     * description :获取当前时间（格式：YYYY-MM-DD hh:mm:ss:mss）
     * author : wuhengzhen
     * date : 2018-11-6 16:35
     */
    public static String getCurrentDateTimeMillis() {
        JDateTime jdt = new JDateTime();
        return jdt.toString(JDateTime.DEFAULT_FORMAT);
    }

    /**
     * 将指定的日期转换为YYYY-MM-DD格式
     *
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String dateStr) {
        String pattern = PATTERN_STANDARD10H;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("wrong date format, should be " + pattern);
        }

        return date;
    }

    /**
     * 日期格式互相转换
     * yyyyMMdd --> yyyy-MM-dd
     * yyyy-MM-dd --> yyyyMMdd
     *
     * @param date         日期字符串
     * @param sourceFormat 源格式
     * @param targetFormat 目标格式
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String formatStrDate(String date, String sourceFormat, String targetFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
        formatter.setLenient(false);
        Date newDate = formatter.parse(date);
        formatter = new SimpleDateFormat(targetFormat);
        return formatter.format(newDate);
    }

    /**
     * 描述：去除日期字串中原“-”和“:”
     *
     * @param dateTime 日期字串
     * @return
     */
    public static String formatString(String dateTime) {
        if ((dateTime != null) && (dateTime.length() >= 8)) {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            return date;
        }
        return "";
    }

    /**
     * 获取想要的时间格式
     *
     * @param dateStr
     * @param wantFormat
     * @return
     */
    public static String getWantDate(String dateStr, String wantFormat) {
        if (!"".equals(dateStr) && dateStr != null) {
            String pattern;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 10:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD10H : PATTERN_STANDARD10X;
                    break;
                case 16:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD16H : PATTERN_STANDARD16X;
                    break;
                case 19:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD19H : PATTERN_STANDARD19X;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    /**
     * 描述：获取指定日期的中文星期数
     *
     * @param date 指定日期
     * @return 星期数，如：星期一
     */
    public static String getWeekStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(7);
        --week;
        String weekStr = "";
        switch (week) {
            case 0:
                weekStr = "星期日";
                break;
            case 1:
                weekStr = "星期一";
                break;
            case 2:
                weekStr = "星期二";
                break;
            case 3:
                weekStr = "星期三";
                break;
            case 4:
                weekStr = "星期四";
                break;
            case 5:
                weekStr = "星期五";
                break;
            case 6:
                weekStr = "星期六";
                break;
            default:

        }
        return weekStr;
    }

    /**
     * 描述：获取当前周
     *
     * @return
     */
    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(7);
        --week;
        if (week == 0) {
            week = 7;
        }
        return week;
    }

    /**
     * 描述：获取中文当前周
     *
     * @return
     */
    public static String getCurrentWeekStr() {
        return getWeekStr(new Date());
    }

    /**
     * 描述：获取本年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1);
    }

    /**
     * 描述：获取本月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1;
    }

    /**
     * 描述：获取本月的当前日期数
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(5);
    }

    /**
     * @param dateStr
     * @param minute
     * @return
     * @Title: getAfterTime
     * @Description: 获取该时间的几分钟之后的时间
     * @author wuhengzhen
     */
    public static String getAfterTime(String dateStr, int minute) {
        String returnStr;
        try {
            String pattern;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() + (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * @param dateStr
     * @param minute
     * @return
     * @Title: getBeforeTime
     * @Description: 获取该时间的几分钟之前的时间
     * @author wuhengzhen
     */
    public static String getBeforeTime(String dateStr, int minute) {
        String returnStr;
        try {
            String pattern;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() - (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }
}
