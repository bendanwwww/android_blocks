package com.androidblocks.Listener;

import java.util.List;

import com.androidblocks.blocks.ScheduleListActivity;
import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ActivityEnum;

import android.app.Activity;
import android.content.Intent;

/**
 * 底部tab按钮监听器
 *
 * @author lsy
 */
public class TabButtonListener extends AbstractListener {

    public TabButtonListener(List<float[]> buttonX, List<float[]> buttonY) {
        super(buttonX, buttonY);
    }

    @Override
    public void downAction(int i) {
        Activity mainActivity = GlobalVariable.getActivity(ActivityEnum.MAIN_ACTIVITY);
        // 跳转编辑页
        Intent intent = new Intent(mainActivity, ScheduleListActivity.class);
        mainActivity.startActivity(intent);
    }

    @Override
    public void moveAction(int i) {

    }

    @Override
    public void upAction(int i) {

    }
}
