package com.androidblocks.view;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.androidblocks.blocks.R;
import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.utils.DateUtils;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.vo.BlockInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class SegmentationArc extends View {

    private static final float ROUND_DEGREE = 360f;

    private static final String TAG = "SegmentationArc";

    private Context mContext;

    /** 圆弧画笔 */
    private Paint paint;
    /** 文字画笔 */
    private Paint textPaint;
    /** 画布 */
    private RectF rectf;
    /** 圆弧宽度 */
    private int arcWidth;
    /** 每一个弧的开始间隔和结束间隔 */
    private float pieceGap = 3f;
    /** 区间信息 */
    private List<BlockInfo> blockInfoList;
    /** 区间占比 */
    private Float[] pieceProportions;
    /** 区间颜色 */
    private Integer[] pieceColors;
    /** 开始角度 */
    private float startAngle = 270f - pieceGap;
    /** 距边框距离倍数 */
    private float rectGap = 4f;


    public SegmentationArc(Context context) {
        super(context);
        this.mContext = context;
    }

    public SegmentationArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setArcAttributes(attrs);

    }

    public SegmentationArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setArcAttributes(attrs);
    }

    /**
     * 根据当前时间计算指针
     * @return
     */
    private float getTimeProportions() {
        return (float) DateUtils.secondsForDay() / (float) DateUtils.DAY_IN_SECOND;
    }

    /**
     * 计算当前时间占圆弧的比例
     * @return
     */
    private float getTimeInRoundProportions() {
        return getTimeProportions() * (ROUND_DEGREE - pieceGap * pieceProportions.length);
    }

    /**
     * 获取区间占比
     * @return
     */
    private Float[] getStandardProportions() {
        float oneProportion = (ROUND_DEGREE - pieceGap * pieceProportions.length) / (float) Arrays.stream(pieceProportions).mapToDouble(Float::doubleValue).sum();
        return Arrays.stream(pieceProportions).map(p -> p * oneProportion).toArray(Float[]::new);
    }

    /**
     * 绘制区间圆弧
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        Float[] standardProportions = getStandardProportions();
        // 设置圆弧风格和圆的宽度
        paint.setPathEffect(null);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(arcWidth);
        paint.setStrokeCap(Paint.Cap.BUTT);
        // 背景为灰色
        paint.setColor(getResources().getColor(R.color.black1));
        float firstStart = startAngle + pieceGap;
        for (int i = 0 ; i < standardProportions.length ; i++) {
            // 绘制圆弧
            canvas.drawArc(rectf, firstStart, standardProportions[i], false, paint);
            firstStart += standardProportions[i] + pieceGap;
        }
    }

    /**
     * 绘制区间颜色 已过区间灰色 未过区间存在色彩
     * @param canvas
     */
    public void drawPieceColor(Canvas canvas) {
        // 获取当前时间占圆弧的比例
        float timeProportions = getTimeInRoundProportions();
//        System.out.println(timeProportions);
        // 获取标准区间占比
        Float[] standardProportions = getStandardProportions();
        // 计算从哪个色块画起
        int colorIndex = 0;
        float firstStart = startAngle + pieceGap;
        for (int i = 0 ; i < standardProportions.length ; i++) {
            if (timeProportions >= standardProportions[i]) {
                timeProportions -= standardProportions[i];
                firstStart = firstStart + standardProportions[i] + pieceGap;
            } else {
                firstStart = firstStart + timeProportions;
                colorIndex = i;
                break;
            }
        }
        // 记录横线位置
        float lineIndex = firstStart;
        // 从当前时间位置开始向后绘制颜色
        for (int i = colorIndex ; i < standardProportions.length ; i++) {
            float firstEnd = standardProportions[i];
            if (i == colorIndex) {
                firstEnd = standardProportions[i] - timeProportions;
            }
            int color = pieceColors[i];
            paint.setColor(getResources().getColor(color));
            canvas.drawArc(rectf, firstStart, firstEnd, false, paint);
            firstStart += firstEnd + pieceGap;
        }
        // 绘制横线
        paint.setColor(getResources().getColor(pieceColors[colorIndex]));
        paint.setStrokeWidth(PaintUtils.dip2px(140f));
        paint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawArc(rectf, lineIndex - 3f, 3, false, paint);
    }

    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Float[] standardProportions = getStandardProportions();
        // 设置文字风格
        textPaint.setColor(getResources().getColor(R.color.dy_weight_bg));
        // 日程文字
        float firstStart = startAngle + pieceGap;
        for (int i = 0 ; i < standardProportions.length ; i++) {
            // 圆的内径为 getWidth() / rectGap - arcWidth / 2
            // 圆的外径为 getWidth() / rectGap + arcWidth / 2
            // 圆的中径为 getWidth() / rectGap
            // 圆心坐标为 (getWidth() / 2 , getHeight() / 2)
            // 圆中径上任意一点坐标为
            // x = getWidth() / 2 + getWidth() / rectGap * cos(φ * π / 180)
            // y = getHeight() / 2 - getWidth() / rectGap * sin(φ * π / 180)
            String text = blockInfoList.get(i).getText();
            float scheduleTextSize = 34f;
            float angle = firstStart + standardProportions[i] / 2f;
            float x = (float) (getWidth() / 2f + getWidth() / rectGap * Math.cos(angle * Math.PI / 180f));
            float y = (float) (getHeight() / 2f + getWidth() / rectGap * Math.sin(angle * Math.PI / 180f));
            textPaint.setTextSize(scheduleTextSize);
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/schedule.ttf");
            textPaint.setTypeface(font);
            // 因为文字本身有大小 需要修正坐标
            canvas.drawText(text, x - TextUtils.textLength(text) / 2f * scheduleTextSize, y + scheduleTextSize / 2f, textPaint);
            firstStart += standardProportions[i] + pieceGap;
        }
        // 时间文字
        textPaint.setAntiAlias(true);
        float timeTextSize = 50f;
        float timeTextGap = 20f;
        textPaint.setTextSize(timeTextSize);
        textPaint.setColor(getResources().getColor(R.color.black1));
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/time.ttf");
        textPaint.setTypeface(font);
        SimpleDateFormat sdf1 = new SimpleDateFormat( "yyyy年MM月dd日");
        SimpleDateFormat sdf2 = new SimpleDateFormat( "HH:mm:ss");
        String date = sdf1.format(new Date());
        String time = sdf2.format(new Date());
        String week = DateUtils.getWeekStr();
        canvas.drawText(date, getWidth() / 2f - TextUtils.textLength(date) * timeTextSize / 2f, getHeight() / 2f - timeTextSize * 0.5f - timeTextGap, textPaint);
        canvas.drawText(time, getWidth() / 2f - TextUtils.textLength(time) * timeTextSize / 2f, getHeight() / 2f + timeTextSize / 2f, textPaint);
        canvas.drawText(week, getWidth() / 2f - TextUtils.textLength(week) * timeTextSize / 2f, getHeight() / 2f + timeTextSize * 1.5f + timeTextGap, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        textPaint = new Paint();
        blockInfoList = GlobalVariable.getBlockInfoList();
        pieceProportions = blockInfoList.stream().map(b -> b.getProportions()).toArray(Float[]::new);
        pieceColors = blockInfoList.stream().map(b -> b.getColor()).toArray(Integer[]::new);
//        获取圆的半径
//        arcRadius = getWidth() / 2 - PaintUtils.dip2px(15);
//        rectf = new RectF(getWidth() / 2 - arcRadius + Utils.dip2px(10), MARGINTOP, getWidth() / 2 + arcRadius - Utils.dip2px(10), 2 * arcRadius);
        // 据边框距离
        float x = getWidth() / rectGap;
        float y = getHeight() / rectGap;
        rectf = new RectF(x, y, getWidth() - x, getHeight() - y);
        // 绘制区间
        drawPiece(canvas);
        // 绘制颜色
        drawPieceColor(canvas);
        // 绘制文字
        drawText(canvas);
    }

    private void setArcAttributes(AttributeSet attrs) {
        //此处可使用styleable中自定义属性
//        TypedArray a = mContext.obtainStyledAttributes(attrs,
//                R.styleable.arc);
//        arcWidth = a.getInteger(R.styleable.arcwidth, 25);
//        a.recycle();
    }

    public void setRoundwidth(int roundwidth) {
        this.arcWidth = roundwidth;
    }

}
