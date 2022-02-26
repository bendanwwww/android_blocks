package com.androidblocks.blocks;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ActivityEnum;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.view.ScheduleLineArc;

import android.content.Intent;
import android.os.Bundle;

/**
 * 日程列表
 *
 * @author lsy
 */
public class ScheduleListActivity extends AbstractActivity {

    private static final ActivityEnum TAG = ActivityEnum.LIST_ACTIVITY;

    private ScheduleLineArc scheduleLineArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        initView();
    }

    @Override
    public void onBackPressed() {
        // 更新画布
        GlobalVariable.getView(ViewEnum.SEGMENTATION_ROUND).invalidate();
        // 跳转至主页
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    ActivityEnum ActivityTag() {
        return TAG;
    }

    private void initView() {
        scheduleLineArc = findViewById(R.id.schedule_line);
        scheduleLineArc.setRectHeight(120f);
        scheduleLineArc.setRectLineColorProportion(0.7f);
        scheduleLineArc.setRectChromaticAberration(0.6f);
    }
}
