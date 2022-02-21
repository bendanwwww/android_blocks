package com.androidblocks.view;

import java.util.List;
import java.util.stream.Collectors;

import com.androidblocks.blocks.R;
import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.vo.BlockInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.view.View;

public class ScheduleLineArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.SCHEDULE_LINE;

    private Context mContext;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 区间信息 */
    private List<BlockInfo> blockInfoList;
    /** 矩形高度 */
    private float rectHeight;
    /** 矩形颜色比例 */
    private float rectLineColorProportion;
    /** 矩形色差 */
    private float rectChromaticAberration;
    /** 最后按钮的坐标 */
    private float[] buttonX = new float[2];
    private float[] buttonY = new float[2];

    public ScheduleLineArc(Context context) {
        super(context);
        this.mContext = context;
    }

    public ScheduleLineArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setArcAttributes(attrs);
    }

    public ScheduleLineArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setArcAttributes(attrs);
    }

    private void drawRect(Canvas canvas) {
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        // 过滤空闲时间
        blockInfoList = blockInfoList.stream().filter(b -> !b.getIdle()).collect(Collectors.toList());
        // 绘制表头
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), rectHeight, paint);
        // 绘制表头文字
        float textSize = 46f;
        float textX = 30f;
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_list_title.ttf");
        textPaint.setTypeface(titleFont);
        textPaint.setTextSize(textSize);
        textPaint.setColor(getResources().getColor(R.color.purple_200));
        canvas.drawText("计划完成事项", textX, (rectHeight + textSize) / 2f, textPaint);
        canvas.drawText("已用时", getWidth() * rectLineColorProportion + textX, (rectHeight + textSize) / 2f, textPaint);
        for (int i = 0 ; i < blockInfoList.size() ; i++) {
            BlockInfo blockInfo = blockInfoList.get(i);
            int color = getResources().getColor(blockInfo.getColor());
            // 第一个矩形
            paint.setColor(color);
            float top = rectHeight * (i + 1);
            float right = getWidth() * rectLineColorProportion;
            canvas.drawRect(0, top, right, top + rectHeight, paint);
            // 第二个矩形
            int[] changeArgb = PaintUtils.changeColorDepth(color, rectChromaticAberration);
            paint.setColor(Color.argb(changeArgb[0], changeArgb[1], changeArgb[2], changeArgb[3]));
            float left = right;
            canvas.drawRect(left, top, getWidth(), top + rectHeight, paint);
            // 绘制矩形文字
            Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
            String lineText = blockInfo.getText() + " ("+ blockInfo.getStartHour() +" ～ "+ blockInfo.getEndHour() +")";
            textPaint.setTypeface(lineFont);
            textPaint.setColor(getResources().getColor(R.color.dy_weight_bg));
            canvas.drawText(lineText, textX, top + (rectHeight + textSize) / 2f, textPaint);
            canvas.drawText("50%", getWidth() - textSize * TextUtils.textLength("50%") - textX, top + (rectHeight + textSize) / 2f, textPaint);
        }
        // 绘制按钮
        String buttonText = "+";
        float buttonSize = 50f;
        float buttonTop = rectHeight * (blockInfoList.size() + 1);
        paint.setColor(getResources().getColor(R.color.purple_700));
        textPaint.setTextSize(buttonSize);
        textPaint.setColor(getResources().getColor(R.color.dy_weight_bg));
        canvas.drawRect(0, buttonTop, getWidth(), buttonTop + rectHeight, paint);
        canvas.drawText(buttonText, (getWidth() + TextUtils.textLength(buttonText)) / 2f, buttonTop + (rectHeight + textSize) / 2f, textPaint);
        // 记录一下按钮的坐标 用于监听点击事件
        buttonX = new float[]{0f, getWidth()};
        buttonY = new float[]{buttonTop, buttonTop + rectHeight};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        blockInfoList = GlobalVariable.getBlockInfoList(BlockInfo.EffectType.WORK_DAY);
//        rectf = new RectF(0, 0, getWidth(), getHeight());
        // 绘制矩形
        drawRect(canvas);
    }

    @Override
    ViewEnum viewTag() {
        return TAG;
    }

    private void setArcAttributes(AttributeSet attrs) {
        //此处可使用styleable中自定义属性
//        TypedArray a = mContext.obtainStyledAttributes(attrs,
//                R.styleable.arc);
//        arcWidth = a.getInteger(R.styleable.arcwidth, 25);
//        a.recycle();
    }

    public void setRectHeight(float rectHeight) {
        this.rectHeight = rectHeight;
    }

    public void setRectLineColorProportion(float rectLineColorProportion) {
        this.rectLineColorProportion = rectLineColorProportion;
    }

    public void setRectChromaticAberration(float rectChromaticAberration) {
        this.rectChromaticAberration = rectChromaticAberration;
    }

    public float[] getButtonX() {
        return buttonX;
    }

    public float[] getButtonY() {
        return buttonY;
    }
}
