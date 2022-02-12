package com.androidblocks.utils;

/**
 * 文本工具类
 *
 * @author lsy
 */
public class TextUtils {

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
}
