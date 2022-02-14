package com.androidblocks.view;

import java.util.List;
import java.util.stream.Collectors;

import com.androidblocks.blocks.R;
import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.vo.BlockInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class ScheduleLineArc extends View {

    private static final String TAG = "ScheduleLineArc";

    private Context mContext;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 画布 */
    private RectF rectf;
    /** 区间信息 */
    private List<BlockInfo> blockInfoList;
    /** 矩形高度 */
    private float rectHeight;
    /** 矩形颜色比例 */
    private float rectLineColorProportion;
    /** 矩形色差 */
    private float rectChromaticAberration;

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
        blockInfoList = blockInfoList.stream().filter(b -> !b.isIdle()).collect(Collectors.toList());
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
            textPaint.setTypeface(lineFont);
            textPaint.setColor(getResources().getColor(R.color.dy_weight_bg));
            canvas.drawText(blockInfo.getText(), textX, top + (rectHeight + textSize) / 2f, textPaint);
            canvas.drawText("50%", getWidth() - textSize * TextUtils.textLength("50%") - textX, top + (rectHeight + textSize) / 2f, textPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        blockInfoList = GlobalVariable.getBlockInfoList();
//        rectf = new RectF(0, 0, getWidth(), getHeight());
        // 绘制矩形
        drawRect(canvas);
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
}
