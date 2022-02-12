package com.androidblocks.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 *
 * @author lsy
 */
public class DateUtils {

    /** 一天的秒数 */
    public static final long DAY_IN_SECOND = 24L * 60L * 60L;
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

    public static String getWeekStr() {
        Calendar calendar = Calendar.getInstance();
        return weekMap.get(calendar.get(Calendar.DAY_OF_WEEK));
    }
}
