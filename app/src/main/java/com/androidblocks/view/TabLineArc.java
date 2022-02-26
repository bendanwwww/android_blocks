package com.androidblocks.view;

import java.util.ArrayList;
import java.util.List;

import com.androidblocks.Listener.TabButtonListener;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.TextUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 主页下方tab画布
 *
 * @author lsy
 */
public class TabLineArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.TAG_LINE;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 矩形边距间隔 */
    private float rectTopGap;

    public TabLineArc(Context context) {
        super(context);
    }

    public TabLineArc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLineArc(Context context, AttributeSet attrs, int defStyleAttr) {
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
        float tabWidth = getWidth() / 3f;
        // 第一个tab
        canvas.drawRect(0, rectTopGap, tabWidth, getHeight() - rectTopGap, paint);
        buttonXs.add(new float[]{0, tabWidth});
        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 第二个tab
        canvas.drawRect(tabWidth, rectTopGap, tabWidth * 2, getHeight() - rectTopGap, paint);
//        buttonXs.add(new float[]{tabWidth, tabWidth * 2});
//        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 第三个tab
        canvas.drawRect(tabWidth * 2, rectTopGap, getWidth(), getHeight() - rectTopGap, paint);
//        buttonXs.add(new float[]{tabWidth * 2, getWidth()});
//        buttonYs.add(new float[]{rectTopGap, getHeight() - rectTopGap});
        // 绘制文字
        Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
        float iconSize = 30f;
        textPaint.setTypeface(lineFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(iconSize);
        canvas.drawText("编辑页签图标", (tabWidth - iconSize * TextUtils.textLength("编辑页签图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
        canvas.drawText("主页页签图标", (tabWidth * 3 - iconSize * TextUtils.textLength("主页页签图标")) / 2f, (getHeight()  + iconSize) / 2f, textPaint);
        canvas.drawText("待办页签图标", (tabWidth * 2 + getWidth() - iconSize * TextUtils.textLength("待办页签图标")) / 2f, (getHeight()  + iconSize) / 2f, textPaint);
        this.setOnTouchListener(new TabButtonListener(buttonXs, buttonYs));
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
}
