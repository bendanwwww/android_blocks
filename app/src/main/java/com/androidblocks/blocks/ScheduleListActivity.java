package com.androidblocks.blocks;

import com.androidblocks.utils.PaintUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.view.ScheduleLineArc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 日程列表
 *
 * @author lsy
 */
public class ScheduleListActivity extends AppCompatActivity {

    private ScheduleLineArc scheduleLineArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        initView();
    }

    private void initView() {
        scheduleLineArc = findViewById(R.id.schedule_line);
        scheduleLineArc.setRectHeight(120f);
        scheduleLineArc.setRectLineColorProportion(0.7f);
        scheduleLineArc.setRectChromaticAberration(0.6f);
        scheduleLineArc.setOnTouchListener((v, e) -> {
            float downX = e.getX();
            float downY = e.getY();
            float[] buttonX = scheduleLineArc.getButtonX();
            float[] buttonY = scheduleLineArc.getButtonY();
            // 判断是否在按钮坐标内
            if (downX < buttonX[0] || downX > buttonX[1] || downY < buttonY[0] || downY > buttonY[1]) {
                return true;
            }
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("downX: " + downX + " downY: " + downY);
                    // 跳转编辑页
                    Intent intent = new Intent(this, ScheduleFormActivity.class);
                    intent.putExtra("id", TextUtils.initId());
                    startActivity(intent);
                    break;
                case MotionEvent.ACTION_MOVE:
//                    System.out.println("移动");
                    break;
                case MotionEvent.ACTION_UP:
//                    System.out.println("松开了");
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}
