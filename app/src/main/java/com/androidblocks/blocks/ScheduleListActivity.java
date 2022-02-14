package com.androidblocks.blocks;

import com.androidblocks.view.ScheduleLineArc;

import android.os.Bundle;
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
    }
}
