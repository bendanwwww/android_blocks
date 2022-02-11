package com.androidblocks.utils;

import java.text.SimpleDateFormat;

/**
 * 时间工具类
 *
 * @author lsy
 */
public class DateUtils {

    /** 一天的秒数 */
    public static final long DAY_IN_SECOND = 24L * 60L * 60L;

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
}
