package com.androidblocks.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.androidblocks.blocks.R;

import android.content.res.Resources;
import android.graphics.Color;

public class PaintUtils {

    /** 圆度数 */
    private static final float ROUND_DEGREE = 360f;
    /** 一些颜色集合 */
    private static final List<Integer> colorList = new ArrayList<Integer>(){{
        add(R.color.smart_color_blue1);
        add(R.color.colorAccent);
        add(R.color.colorPrimary);
        add(R.color.colorPrimaryDark);
        add(R.color.main_top_color2);
        add(R.color.main_top_color3);
        add(R.color.main_top_color4);
        add(R.color.sleep_color);
        add(R.color.teal_200);
        add(R.color.teal_700);
        add(R.color.smart_color_orange12);
        add(R.color.smart_color_orange1);
        add(R.color.smart_color_blue5);
        add(R.color.smart_color_red3);
        add(R.color.smart_color_red8);
        add(R.color.smart_color_green1);
        add(R.color.smart_color_green8);
        add(R.color.smart_color_purple1);
        add(R.color.smart_color_purple8);
        add(R.color.smart_color_54);
        add(R.color.smart_color_44);
        add(R.color.smart_color_31);
    }};

    public static float getRoundDegree() {
        return ROUND_DEGREE;
    }

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

    /**
     * 随机生成一种颜色
     * @return
     */
    public static int randomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }

    /**
     * 随机生成一种颜色
     * @return
     */
    public static int randomColorByResources() {
        Random random = new Random();
        return colorList.get(random.nextInt(colorList.size()));
    }

}
