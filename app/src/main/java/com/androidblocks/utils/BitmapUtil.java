package com.androidblocks.utils;

import java.util.Objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

/**
 * 像素工具类
 *
 * @author lsy
 */
public class BitmapUtil {

    public static float getBitMapWidth(Resources resources, int id) {
        Bitmap bitmap = ((BitmapDrawable) resources.getDrawable(id)).getBitmap();
        if (Objects.isNull(bitmap)) {
            return 0;
        }
        return bitmap.getWidth();
    }

    public static float getBitMapHeight(Resources resources, int id) {
        Bitmap bitmap = ((BitmapDrawable) resources.getDrawable(id)).getBitmap();
        if (Objects.isNull(bitmap)) {
            return 0;
        }
        return bitmap.getHeight();
    }

    public static Bitmap getBitMap(Resources resources, int id) {
        return ((BitmapDrawable) resources.getDrawable(id)).getBitmap();
    }

    public static Bitmap getBitMapWithMultiple(Resources resources, int id, float multiple) {
        Bitmap bitmap = ((BitmapDrawable) resources.getDrawable(id)).getBitmap();
        if (Objects.isNull(bitmap)) {
            return null;
        }
        return fitBitmap(bitmap, bitmap.getWidth() * multiple);
    }

    public static Bitmap getBitMapWithWidth(Resources resources, int id, float newWidth) {
        Bitmap bitmap = ((BitmapDrawable) resources.getDrawable(id)).getBitmap();
        if (Objects.isNull(bitmap)) {
            return null;
        }
        return fitBitmap(bitmap, newWidth);
    }

    /**
     * 设置固定的宽度，高度随之变化，使图片不会变形
     * @param target 需要转化bitmap参数
     * @param newWidth 设置新的宽度
     * @return
     */
    public static Bitmap fitBitmap(Bitmap target, float newWidth) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = newWidth / width;
        // float scaleHeight = ((float)newHeight) / height;
        int newHeight = (int) (scaleWidth * height);
        matrix.postScale(scaleWidth, scaleWidth);
        // Bitmap result = Bitmap.createBitmap(target,0,0,width,height,
        // matrix,true);
//        if (target != null && !target.equals(bmp) && !target.isRecycled()) {
//            target.recycle();
//            target = null;
//        }
        return Bitmap.createBitmap(target, 0, 0, width, height, matrix, true);
    }
}
