package com.androidblocks.blocks;

import java.util.Objects;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.DateUtils;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.vo.BlockInfo;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 日程表单
 *
 * @author lsy
 */
public class ScheduleFormActivity extends AppCompatActivity {

    private LinearLayout mainLinerLayout;

    private String time1;
    private String time2;
    private String time3;
    private String time4;
    private String briefly = "";
    private String scheduleInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        mainLinerLayout = this.findViewById(R.id.scheduleForm);
        // 监听各个文本框文字变化
        ((EditText) this.findViewById(R.id.time1)).addTextChangedListener(new EditTextListener((s) -> time1 = s));
        ((EditText) this.findViewById(R.id.time2)).addTextChangedListener(new EditTextListener((s) -> time2 = s));
        ((EditText) this.findViewById(R.id.time3)).addTextChangedListener(new EditTextListener((s) -> time3 = s));
        ((EditText) this.findViewById(R.id.time4)).addTextChangedListener(new EditTextListener((s) -> time4 = s));
        ((EditText) this.findViewById(R.id.briefly)).addTextChangedListener(new EditTextListener((s) -> briefly = s));
        ((EditText) this.findViewById(R.id.scheduleInfo)).addTextChangedListener(new EditTextListener((s) -> scheduleInfo = s));
        // 开启线程
        ScheduleFormActivity.EditThread thread = new ScheduleFormActivity.EditThread();
        thread.start();
    }

    class EditThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    save();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 保存写入的日程信息
        public void save() {
            // 判断时间是否填写完整且合法
            if (!TextUtils.allFull(time1, time2, time3, time4) || !TextUtils.allNumber(time1, time2, time3, time4)) {
                return;
            }
            // 组合时间
            String startHour = "";
            String endHour = "";
            try {
                startHour = DateUtils.getHour(time1, time2);
                endHour = DateUtils.getHour(time3, time4);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            // 判断时间先后顺序
            if (!DateUtils.afterHour(startHour, endHour)) {
                return;
            }
            // 存入blockInfo
            String id = getIntent().getStringExtra("id");
            boolean needRefreshView = false;
            BlockInfo blockInfo = GlobalVariable.getBlockInfoById(id);
            if (Objects.isNull(blockInfo)) {
                blockInfo = new BlockInfo(id, BlockInfo.EffectType.WORK_DAY, briefly, scheduleInfo, PaintUtils.randomColorByResources(), startHour, endHour, 0f);
                needRefreshView = true;
            } else {
                needRefreshView = blockInfo.updateObject(briefly, scheduleInfo, blockInfo.getColor(), startHour, endHour);
            }
            GlobalVariable.setBlockInfo(blockInfo);
            // 更新轮子和列表画布
            if (needRefreshView) {
                GlobalVariable.getView(ViewEnum.SEGMENTATION_ROUND).invalidate();
                GlobalVariable.getView(ViewEnum.SCHEDULE_LINE).invalidate();
            }
        }
    }

    public interface EditChangeCallBack {
        void change(String s);
    }

    class EditTextListener implements TextWatcher {

        private EditChangeCallBack callBack;

        public EditTextListener(EditChangeCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            callBack.change(s.toString());
        }
    }

}