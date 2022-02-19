package com.androidblocks.utils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author lsy
 */
public class DateUtils {

    /** 一天的秒数 */
    public static final long DAY_IN_SECOND = 24L * 60L * 60L;
    /** 一天开始的时刻 */
    public static final String FIRST_HOUR_DAY = "00:00";
    /** 一天结束的时刻 */
    public static final String END_HOUR_DAY = "23:59";
    /** 时间格式 */
    public static final String DAY_HOUR_FORMAT_STR = "yyyy-MM-dd {0}:00";
    /** 星期 */
    public static final Map<Integer, String> weekMap = new HashMap<Integer, String>(){{
        put(1, "星期日");
        put(2, "星期一");
        put(3, "星期二");
        put(4, "星期三");
        put(5, "星期四");
        put(6, "星期五");
        put(7, "星期六");
    }};
    /** 时间分隔符 */
    public static final String TIME_SPLIT = ":";
    /** 格式化时间对象 */
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




    /**
     * 获取当日已过秒数
     * @return
     */
    public static long secondsForDay() {
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
            return (now - (sdfOne.parse(sdfOne.format(now)).getTime())) / 1000;
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获取当前时间的星期数
     * @return
     */
    public static String getWeekStr() {
        Calendar calendar = Calendar.getInstance();
        return weekMap.get(calendar.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 组装时刻
     * @param time1
     * @param time2
     * @return
     */
    public static String getHour(String time1, String time2) {
        String hour = time1 + TIME_SPLIT + time2;
        // 判断时间是否合法
        try {
            HOUR_FORMAT.parse(hour);
        } catch (Exception e) {
            throw new RuntimeException("时间格式无效");
        }
        return hour;
    }

    /**
     * 判断第一个时间是否在第二个时间之前
     * @param time1
     * @param time2
     * @return
     */
    public static boolean afterHour(String time1, String time2) {
        try {
            Date d1 = HOUR_FORMAT.parse(time1);
            Date d2 = HOUR_FORMAT.parse(time2);
            return d1.before(d2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取时刻的时间戳
     * @param hour
     * @return
     */
    public static long getHourTimestamp(String hour) {
        try {
            String dayStr = DateFormatUtils.format(new Date(), MessageFormat.format(DAY_HOUR_FORMAT_STR, hour));
            return DAY_FORMAT.parse(dayStr).getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获取时间戳的时刻
     * @param timestamp
     * @return
     */
    public static String getTimestampToHour(long timestamp) {
        try {
            return HOUR_FORMAT.format(timestamp);
        } catch (Exception e) {
            return FIRST_HOUR_DAY;
        }
    }

    /**
     * 获取一天起始时刻
     * @return
     */
    public static String getFirstHourByDay() {
        return FIRST_HOUR_DAY;
    }

    /**
     * 获取一天结束时刻
     * @return
     */
    public static String getEndHourByDay() {
        return END_HOUR_DAY;
    }
}
