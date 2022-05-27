package com.androidblocks.view;

import java.util.ArrayList;
import java.util.List;

import com.androidblocks.Listener.EditButtonListener;
import com.androidblocks.Listener.TabButtonListener;
import com.androidblocks.blocks.R;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.BitmapUtil;
import com.androidblocks.utils.TextUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 头部图标
 *
 * @author lsy
 */
public class HeadLineArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.HEAD_LINE;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 矩形边距间隔 */
    private float rectTopGap;
    /** 气泡框占比 */
    private float textBoxProportions;
    /** icon 间隔 */
    private float iconGap;

    public HeadLineArc(Context context) {
        super(context);
    }

    public HeadLineArc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadLineArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 绘制矩形
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        // 点击坐标
        List<float[]> buttonXs = new ArrayList<>();
        List<float[]> buttonYs = new ArrayList<>();

        float textBoxWidth = getWidth() * textBoxProportions;
        float otherTab = getWidth() * (1f - textBoxProportions) / 2;
        // 绘制气泡框
//        canvas.drawRect(0, rectTopGap, textBoxWidth, getHeight() - rectTopGap, paint);
        // 绘制社群按钮
        Bitmap share = BitmapUtil.getBitMapWithWidth(getResources(), R.drawable.share, otherTab - iconGap * 2);
        canvas.drawBitmap(share, textBoxWidth + iconGap, (getHeight() - share.getHeight()) / 2, paint);
//        canvas.drawRect(textBoxWidth, rectTopGap, textBoxWidth + otherTab, getHeight() - rectTopGap, paint);
        // 绘制设置按钮
        Bitmap setting = BitmapUtil.getBitMapWithWidth(getResources(), R.drawable.setting, otherTab - iconGap * 2);
        canvas.drawBitmap(setting, textBoxWidth + otherTab + iconGap, (getHeight() - setting.getHeight()) / 2, paint);
        buttonXs.add(new float[]{textBoxWidth + otherTab + iconGap, textBoxWidth + otherTab + iconGap + setting.getWidth()});
        buttonYs.add(new float[]{(getHeight() - setting.getHeight()) / 2, (getHeight() - setting.getHeight()) / 2 + setting.getHeight()});
//        canvas.drawRect(textBoxWidth + otherTab, rectTopGap, getWidth(), getHeight() - rectTopGap, paint);
        // 绘制文字
        Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
        float iconSize = 30f;
        textPaint.setTypeface(lineFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(iconSize);
//        canvas.drawText("气泡框组件", (textBoxWidth - iconSize * TextUtils.textLength("气泡框组件")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
//        canvas.drawText("社群图标", (2f * textBoxWidth + otherTab - iconSize * TextUtils.textLength("社群图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
//        canvas.drawText("分享图标", (textBoxWidth + otherTab + getWidth() - iconSize * TextUtils.textLength("分享图标")) / 2f, (getHeight() + iconSize) / 2f, textPaint);
        // 添加监听器
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

    public void setTextBoxProportions(float textBoxProportions) {
        this.textBoxProportions = textBoxProportions;
    }

    public void setIconGap(float iconGap) {
        this.iconGap = iconGap;
    }
}
