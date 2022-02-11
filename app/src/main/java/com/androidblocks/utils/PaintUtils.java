package com.androidblocks.utils;

import android.content.res.Resources;

public class PaintUtils {
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
