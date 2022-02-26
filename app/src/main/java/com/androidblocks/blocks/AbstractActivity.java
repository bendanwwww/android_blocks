package com.androidblocks.blocks;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.enums.ActivityEnum;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 页面抽象
 *
 * @author lsy
 */
public abstract class AbstractActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全局保存页面
        GlobalVariable.setActivityMap(ActivityTag(), this);
    }

    abstract ActivityEnum ActivityTag();
}
