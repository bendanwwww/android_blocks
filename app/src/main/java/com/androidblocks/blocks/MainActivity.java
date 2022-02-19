package com.androidblocks.blocks;

import com.androidblocks.utils.PaintUtils;
import com.androidblocks.view.SegmentationArc;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 主页 (轮子)
 *
 * @author lsy
 */
public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private SegmentationArc segmentationArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_gulugulu_wheel);
        initView();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                segmentationArc.invalidate();
            }
        };
        TimeThread thread = new TimeThread();
        thread.start();
    }

    public void onclick(View view) {
        Intent intent = new Intent(this,ScheduleListActivity.class);
        startActivity(intent);
    }

    private void initView() {
        segmentationArc = findViewById(R.id.gulugulu);
        segmentationArc.setRoundwidth(PaintUtils.dip2px(80));
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    handler.sendMessage(handler.obtainMessage(100, ""));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}