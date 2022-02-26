package com.androidblocks.Listener;

import java.util.List;

import com.androidblocks.blocks.MainActivity;
import com.androidblocks.blocks.ScheduleFormActivity;
import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ActivityEnum;
import com.androidblocks.utils.TextUtils;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 日程编辑按钮监听器
 *
 * @author lsy
 */
public class EditButtonListener extends AbstractListener {

    private List<String> ids;

    public EditButtonListener(List<float[]> buttonX, List<float[]> buttonY, List<String> ids) {
        super(buttonX, buttonY);
        this.ids = ids;
    }

    @Override
    public void downAction(int i) {
        Activity listActivity = GlobalVariable.getActivity(ActivityEnum.LIST_ACTIVITY);
        // 跳转编辑页
        Intent intent = new Intent(listActivity, ScheduleFormActivity.class);
        intent.putExtra("id", ids.get(i));
        listActivity.startActivity(intent);
    }

    @Override
    public void moveAction(int i) {

    }

    @Override
    public void upAction(int i) {

    }
}
