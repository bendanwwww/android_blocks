package com.androidblocks.blocks;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.dao.BlockInfoDAO;
import com.androidblocks.db.DaoFactory;
import com.androidblocks.threads.BlockInfoThread;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.view.SegmentationArc;
import com.androidblocks.vo.BlockInfo;

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
//        getApplicationContext().deleteDatabase(BlockInfo.class.getSimpleName());
        // 初始化日程
        initBlock();
        // 初始化视图
        initView();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                segmentationArc.invalidate();
            }
        };
        TimeThread thread = new TimeThread();
        Thread blockInfoThread = new Thread(new BlockInfoThread(getApplicationContext()));
        thread.start();
        blockInfoThread.start();
    }

    public void onclick(View view) {
        Intent intent = new Intent(this,ScheduleListActivity.class);
        startActivity(intent);
    }

    private void initBlock() {
        BlockInfoDAO blockInfoDAO = (BlockInfoDAO) DaoFactory.getDao(BlockInfoDAO.class, getApplicationContext());
        List<BlockInfo> blockInfoList = blockInfoDAO.queryAll();
        if (CollectionUtils.isNotEmpty(blockInfoList)) {
            BlockInfo[] blockInfos = new BlockInfo[blockInfoList.size()];
            GlobalVariable.setBlockInfo(blockInfoList.toArray(blockInfos));
        }

    }

    private void initView() {
        getApplicationContext();
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