package com.androidblocks.blocks;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.dao.BlockInfoDAO;
import com.androidblocks.db.DaoFactory;
import com.androidblocks.enums.ActivityEnum;
import com.androidblocks.threads.BlockInfoThread;
import com.androidblocks.utils.PaintUtils;
import com.androidblocks.view.DayChangeLineArc;
import com.androidblocks.view.DaySlideArc;
import com.androidblocks.view.HeadLineArc;
import com.androidblocks.view.SegmentationArc;
import com.androidblocks.view.TabLineArc;
import com.androidblocks.vo.BlockInfo;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * 主页 (轮子)
 *
 * @author lsy
 */
public class MainActivity extends AbstractActivity {

    private static final ActivityEnum TAG = ActivityEnum.MAIN_ACTIVITY;

    private static final float[] VIEW_PROPORTIONS = {0.12f, 0.1f, 0.6f, 0.08f, 0.1f};
    private static final float ROUND_WIDTH_PROPORTIONS = 0.086f;

    private Handler handler;
    private SegmentationArc segmentationArc;
    private DayChangeLineArc dayChangeLineArc;
    private TabLineArc tabLineArc;
    private DaySlideArc daySlideArc;
    private HeadLineArc headLineArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_gulugulu_wheel);
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

    @Override
    ActivityEnum ActivityTag() {
        return TAG;
    }

    // TODO 需挪至启动时执行 且执行一次
    private void initBlock() {
        if (CollectionUtils.isNotEmpty(GlobalVariable.getBlockInfoList())) {
            return;
        }
        BlockInfoDAO blockInfoDAO = (BlockInfoDAO) DaoFactory.getDao(BlockInfoDAO.class, getApplicationContext());
        List<BlockInfo> blockInfoList = blockInfoDAO.queryAll();
        if (CollectionUtils.isNotEmpty(blockInfoList)) {
            BlockInfo[] blockInfos = new BlockInfo[blockInfoList.size()];
            GlobalVariable.setBlockInfo(blockInfoList.toArray(blockInfos));
        }
    }

    private void initView() {
        getApplicationContext();
        // 头部控件
        headLineArc = findViewById(R.id.head_line);
        headLineArc.setTextBoxProportions(0.6f);
        headLineArc.setRectTopGap(PaintUtils.dip2px(10f));
        ViewGroup.LayoutParams headLineParams = headLineArc.getLayoutParams();
        // 白天/夜晚切换
        daySlideArc = findViewById(R.id.day_slide);
        daySlideArc.setRectGap(PaintUtils.dip2px(10f));
        ViewGroup.LayoutParams daySlideParams = daySlideArc.getLayoutParams();
        // 日期选择
        dayChangeLineArc = findViewById(R.id.day_change);
        dayChangeLineArc.setRectTopGap(PaintUtils.dip2px(10f));
        dayChangeLineArc.setRectGap(PaintUtils.dip2px(30f));
        ViewGroup.LayoutParams dayChangeLineParams = dayChangeLineArc.getLayoutParams();
        // 下方tab
        tabLineArc = findViewById(R.id.tab);
        tabLineArc.setRectTopGap(PaintUtils.dip2px(10f));
        ViewGroup.LayoutParams tabLineParams = tabLineArc.getLayoutParams();
        // 轮子
        segmentationArc = findViewById(R.id.gulugulu);
        ViewGroup.LayoutParams segmentationParams = segmentationArc.getLayoutParams();
        // 组件适配
        int[] windowsSize = PaintUtils.getWindowsSize(this);
        int width = windowsSize[0];
        int height = windowsSize[1];
        headLineParams.height = (int) (height * VIEW_PROPORTIONS[0]);
        daySlideParams.height = (int) (height * VIEW_PROPORTIONS[1]);
        dayChangeLineParams.height = (int) (height * VIEW_PROPORTIONS[3]);
        tabLineParams.height = (int) (height * VIEW_PROPORTIONS[4]);
        int segmentationSize = (int) (height * VIEW_PROPORTIONS[2]) > width ? width : (int) (height * VIEW_PROPORTIONS[2]);
        segmentationParams.height = segmentationSize;
        segmentationParams.width = segmentationSize;
        float roundWidth = segmentationSize * ROUND_WIDTH_PROPORTIONS;
        segmentationArc.setRectGap(PaintUtils.dip2px(roundWidth / 2f + 20f));
        segmentationArc.setRoundWidth(PaintUtils.dip2px(roundWidth));
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