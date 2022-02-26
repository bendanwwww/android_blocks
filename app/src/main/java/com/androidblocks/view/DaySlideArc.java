package com.androidblocks.view;

import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.TextUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 白天/夜晚切换画布
 *
 * @author lsy
 */
public class DaySlideArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.SLIDER;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 画布 */
    private RectF rectf;
    /** 圆弧弧度 */
    private int arcRadian;
    /** 圆弧高度 */
    private float arcHeight;
    /** 距边框距离 */
    private float rectGap;

    public DaySlideArc(Context context) {
        super(context);
    }

    public DaySlideArc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DaySlideArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 绘制圆弧
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        // 绘制矩形
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(rectGap, rectGap, getWidth() - rectGap, getHeight() - rectGap, paint);
        // 绘制文字
        Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
        float iconSize = 50f;
        textPaint.setTypeface(lineFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(iconSize);
        canvas.drawText("白天/夜晚切换控件", (getWidth() - iconSize * TextUtils.textLength("白天/夜晚切换控件")) / 2f, (getHeight() + iconSize) / 2f, textPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        // 据边框距离
        rectf = new RectF(rectGap, rectGap, getWidth() - rectGap, getHeight() - rectGap);
        // 绘制圆弧
        drawPiece(canvas);
    }

    @Override
    public ViewEnum viewTag() {
        return TAG;
    }

    @Override
    public void setArcAttributes(AttributeSet attrs) {

    }

    public void setArcRadian(int arcRadian) {
        this.arcRadian = arcRadian;
    }

    public void setArcHeight(float arcHeight) {
        this.arcHeight = arcHeight;
    }

    public void setRectGap(float rectGap) {
        this.rectGap = rectGap;
    }
}
