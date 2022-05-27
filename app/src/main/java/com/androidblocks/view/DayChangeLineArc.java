package com.androidblocks.view;

import java.util.ArrayList;
import java.util.List;

import com.androidblocks.blocks.R;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.BitmapUtil;
import com.androidblocks.utils.TextUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 日期切换画布
 *
 * @author lsy
 */
public class DayChangeLineArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.DAY_CHANGE;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 矩形边距间隔 */
    private float rectTopGap;
    /** 矩形间隔 */
    private float rectGap;

    public DayChangeLineArc(Context context) {
        super(context);
    }

    public DayChangeLineArc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DayChangeLineArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void drawRect(Canvas canvas) {
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        // 点击坐标
        List<float[]> buttonXs = new ArrayList<>();
        List<float[]> buttonYs = new ArrayList<>();
        // tab 宽度
        float tabWidth = (getWidth() - 2 * rectGap) / 3f;
        float rectWidth = getWidth() / 3f;
        float gap = (rectWidth - tabWidth) / 2f;
        // 第一个tab
        Bitmap leftArrow = BitmapUtil.getBitMapWithWidth(getResources(), R.drawable.arrow_left, tabWidth - 2 * gap - 2 * rectTopGap);
        canvas.drawBitmap(leftArrow, rectTopGap, 0, paint);
//        canvas.drawRect(gap, rectTopGap, gap + tabWidth, getHeight() - rectTopGap, paint);
        buttonXs.add(new float[]{gap, gap + tabWidth});
        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 第二个tab
        Bitmap calendar = BitmapUtil.getBitMapWithWidth(getResources(), R.drawable.calendar, tabWidth - 2 * gap - 2 * rectTopGap);
        canvas.drawBitmap(calendar, (getWidth() - calendar.getWidth()) / 2, 0, paint);
//        canvas.drawRect(rectWidth + gap, rectTopGap, rectWidth + gap + tabWidth, getHeight() - rectTopGap, paint);
        buttonXs.add(new float[]{rectWidth + gap, rectWidth + gap + tabWidth});
        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 第三个tab
        Bitmap rightArrow = BitmapUtil.getBitMapWithWidth(getResources(), R.drawable.arrow_right, tabWidth - 2 * gap - 2 * rectTopGap);
        canvas.drawBitmap(rightArrow, getWidth() - rightArrow.getWidth() - rectTopGap, 0, paint);
//        canvas.drawRect(rectWidth * 2 + gap, rectTopGap, rectWidth * 2 + gap + tabWidth, getHeight() - rectTopGap, paint);
        buttonXs.add(new float[]{rectWidth * 2 + gap, rectWidth * 2 + gap + tabWidth});
        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 绘制文字
        Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
        float iconSize = 22f;
        textPaint.setTypeface(lineFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(iconSize);
//        canvas.drawText("前一天图标", (tabWidth + gap * 2 - iconSize * TextUtils.textLength("前一天图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
//        canvas.drawText("日期选择控件图标", (rectWidth * 2 + tabWidth + gap * 2 - iconSize * TextUtils.textLength("日期选择控件图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
//        canvas.drawText("后一天图标", (rectWidth * 4 + tabWidth + gap * 2 - iconSize * TextUtils.textLength("后一天图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        // 绘制矩形
        drawRect(canvas);
    }

    @Override
    public ViewEnum viewTag() {
        return TAG;
    }

    @Override
    public void setArcAttributes(AttributeSet attrs) {

    }

    public void setRectTopGap(float rectTopGap) {
        this.rectTopGap = rectTopGap;
    }

    public void setRectGap(float rectGap) {
        this.rectGap = rectGap;
    }
}
