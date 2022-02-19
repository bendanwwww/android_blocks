package com.androidblocks.utils;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 文本工具类
 *
 * @author lsy
 */
public class TextUtils {

    /** 判断数字 正则表达式 */
    private static final String NUMBER_PATTERN = "^[0-9]\\d*$";

    /**
     * 计算字符串真实长度 (字符 = 1, 数字 = 0.5, 符号 = 0.5)
     * @param text
     * @return
     */
    public static float textLength(String text) {
        float res = 0;
        for (char c : text.toCharArray()) {
            if ((c >= 0 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 127)) {
                res += 0.5;
            } else {
                res += 1;
            }
        }
        return res;
    }

    /**
     * 判断每一个字符串是否都不为空
     * @param s
     * @return
     */
    public static boolean allFull(String... s) {
        for (String t : s) {
            if (Objects.isNull(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断每一个字符串是否都为数字 包括多个0组成的字符串
     * @param s
     * @return
     */
    public static boolean allNumber(String... s) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        for (String t : s) {
            if (!pattern.matcher(t).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成一个随机id
     * @return
     */
    public static String initId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
