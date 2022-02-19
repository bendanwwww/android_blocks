package com.androidblocks.view;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ViewEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * 画布抽象
 *
 * @author lsy
 */
public abstract class AbstractView extends View {

    public AbstractView(Context context) {
        super(context);
    }

    public AbstractView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbstractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 全局保存画布
        GlobalVariable.setView(viewTag(), this);
    }

    abstract ViewEnum viewTag();
}
