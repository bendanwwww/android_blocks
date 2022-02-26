package com.androidblocks.Listener;

import java.util.List;

import android.view.MotionEvent;
import android.view.View;

/**
 * 监听器抽象
 *
 * @author lsy
 */
public abstract class AbstractListener implements View.OnTouchListener {

    private List<float[]> buttonXs;
    private List<float[]> buttonYs;

    public AbstractListener(List<float[]> buttonXs, List<float[]> buttonYs) {
        this.buttonXs = buttonXs;
        this.buttonYs = buttonYs;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float downX = event.getX();
        float downY = event.getY();
        int index;
        if ((index = inCoordinate(downX, downY)) < 0) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downAction(index);
                break;
            case MotionEvent.ACTION_MOVE:
                moveAction(index);
                break;
            case MotionEvent.ACTION_UP:
                upAction(index);
                break;
            default:
                break;
        }
        return true;
    }

    public int inCoordinate(float downX, float downY) {
        for (int i = 0 ; i < buttonXs.size() ; i++) {
            float[] buttonX = buttonXs.get(i);
            float[] buttonY = buttonYs.get(i);
            if (downX >= buttonX[0] && downX <= buttonX[1] && downY >= buttonY[0] && downY <= buttonY[1]) {
                return i;
            }
        }
        return -1;
    }

    public abstract void downAction(int i);

    public abstract void moveAction(int i);

    public abstract void upAction(int i);
}
