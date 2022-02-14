package com.androidblocks.utils;

import android.content.res.Resources;
import android.graphics.Color;

public class PaintUtils {

    /**
     * 像素转换
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取颜色码
     * @return
     */
    public static int[] getRGB(int color) {
        int[] argb = new int[4];
        argb[0] = Color.alpha(color);
        argb[1] = Color.red(color);
        argb[2] = Color.green(color);
        argb[3] = Color.blue(color);
        return argb;
    }

    /**
     * 获取颜色码
     * @return
     */
    public static int[] changeColorDepth(int color, float chromaticAberration) {
        int[] argb = new int[4];
        argb[0] = (int) (Color.alpha(color) * chromaticAberration);
        argb[1] = Color.red(color);
        argb[2] = Color.green(color);
        argb[3] = Color.blue(color);
        return argb;
    }

}
