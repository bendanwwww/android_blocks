package com.androidblocks.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.androidblocks.Listener.EditButtonListener;
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

/**
 * 日程列表画布
 *
 * @author lsy
 */
public class ScheduleLineArc extends AbstractView {

    private static final ViewEnum TAG = ViewEnum.SCHEDULE_LINE;

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

    public ScheduleLineArc(Context context) {
        super(context);
    }

    public ScheduleLineArc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScheduleLineArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void drawRect(Canvas canvas) {
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        // 点击坐标
        List<float[]> buttonXs = new ArrayList<>();
        List<float[]> buttonYs = new ArrayList<>();
        // 点击参数
        List<String> params = new ArrayList<>();
        // 过滤空闲时间
        blockInfoList = blockInfoList.stream().filter(b -> !b.getIdle()).collect(Collectors.toList());
        // 绘制表头
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), rectHeight, paint);
        // 绘制表头文字
        float titleIconSize = 30f;
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
        textPaint.setTypeface(titleFont);
        textPaint.setTextSize(titleIconSize);
        textPaint.setColor(Color.BLACK);
        canvas.drawText("计划完成事项图标", (getWidth() * rectLineColorProportion - titleIconSize * TextUtils.textLength("计划完成事项图标")) / 2f, (rectHeight + titleIconSize) / 2f, textPaint);
        canvas.drawText("已用时图标", (getWidth() * rectLineColorProportion + getWidth() - rectHeight - titleIconSize * TextUtils.textLength("已用时图标")) / 2, (rectHeight + titleIconSize) / 2f, textPaint);
        canvas.drawText("编辑图标", (getWidth() * 2 - rectHeight - titleIconSize * TextUtils.textLength("编辑图标")) / 2f, (rectHeight + titleIconSize) / 2f, textPaint);
        for (int i = 0 ; i < blockInfoList.size() ; i++) {
            BlockInfo blockInfo = blockInfoList.get(i);
            int color = getResources().getColor(blockInfo.getColor());
            paint.setStyle(Paint.Style.FILL);
            // 第一个矩形
            paint.setColor(color);
            float top = rectHeight * (i + 1);
            float right = getWidth() * rectLineColorProportion;
            canvas.drawRect(0, top, right, top + rectHeight, paint);
            // 第二个矩形
            int[] changeArgb = PaintUtils.changeColorDepth(color, rectChromaticAberration);
            paint.setColor(Color.argb(changeArgb[0], changeArgb[1], changeArgb[2], changeArgb[3]));
            float left = right;
            canvas.drawRect(left, top, getWidth() - rectHeight, top + rectHeight, paint);
            // 第三个矩形
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(getWidth() - rectHeight, top, getWidth(), top + rectHeight, paint);
            // 绘制矩形文字
            float textSize = 46f;
            float textX = 30f;
            Typeface lineFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule_line.ttf");
            String lineText = blockInfo.getText() + " ("+ blockInfo.getStartHour() +" ～ "+ blockInfo.getEndHour() +")";
            textPaint.setTypeface(lineFont);
            textPaint.setTextSize(textSize);
            textPaint.setColor(getResources().getColor(R.color.dy_weight_bg));
            canvas.drawText(lineText, textX, top + (rectHeight + textSize) / 2f, textPaint);
            canvas.drawText("50%", getWidth() - rectHeight - textSize * TextUtils.textLength("50%") - textX, top + (rectHeight + textSize) / 2f, textPaint);
            float iconSize = 30f;
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(iconSize);
            canvas.drawText("编辑图标", (getWidth() * 2 - rectHeight - iconSize * TextUtils.textLength("编辑图标")) / 2f, top + (rectHeight + iconSize) / 2f, textPaint);
            // 添加事件坐标
            buttonXs.add(new float[]{getWidth() - rectHeight, getWidth()});
            buttonYs.add(new float[]{top, top + rectHeight});
            params.add(blockInfo.getId());
        }
        // 绘制按钮
        String buttonText = "添加日程图标";
        float buttonSize = 50f;
        float buttonTop = rectHeight * (blockInfoList.size() + 1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        textPaint.setTextSize(buttonSize);
        textPaint.setColor(Color.BLACK);
        canvas.drawRect(0, buttonTop, getWidth(), buttonTop + rectHeight, paint);
        canvas.drawText(buttonText, (getWidth() - buttonSize * TextUtils.textLength(buttonText)) / 2f, buttonTop + (rectHeight + buttonSize) / 2f, textPaint);
        // 添加事件坐标
        buttonXs.add(new float[]{0f, getWidth()});
        buttonYs.add(new float[]{buttonTop, buttonTop + rectHeight});
        params.add(TextUtils.initId());
        // 添加监听器
        this.setOnTouchListener(new EditButtonListener(buttonXs, buttonYs, params));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        blockInfoList = GlobalVariable.getBlockInfoList(BlockInfo.EffectType.WORK_DAY);
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
