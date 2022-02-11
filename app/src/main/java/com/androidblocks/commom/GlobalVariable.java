package com.androidblocks.commom;

import java.util.ArrayList;
import java.util.List;

import com.androidblocks.blocks.R;
import com.androidblocks.vo.BlockInfo;

/**
 * 全局参数
 *
 * @author lsy
 */
public class GlobalVariable {

    private static volatile List<BlockInfo> blockInfoList = new ArrayList<>();

    public static List<BlockInfo> getBlockInfoList() {
        blockInfoList = new ArrayList<>();
        blockInfoList.add(new BlockInfo("睡眠", "", R.color.sleep_color, 0l, 0l, 30f));
        blockInfoList.add(new BlockInfo("早餐", "", R.color.main_top_color3, 0l, 0l, 2f));
        blockInfoList.add(new BlockInfo("", "", R.color.white2, 0l, 0l, 4f));
        blockInfoList.add(new BlockInfo("工作", "", R.color.main_top_color2, 0l, 0l, 16f));
        blockInfoList.add(new BlockInfo("午餐", "", R.color.colorPrimary, 0l, 0l, 4f));
        blockInfoList.add(new BlockInfo("工作", "", R.color.colorAccent, 0l, 0l, 16f));
        blockInfoList.add(new BlockInfo("", "", R.color.white2, 0l, 0l, 4f));
        blockInfoList.add(new BlockInfo("工作", "", R.color.main_top_color4, 0l, 0l, 16f));
        blockInfoList.add(new BlockInfo("晚餐", "", R.color.purple_700, 0l, 0l, 5f));
        blockInfoList.add(new BlockInfo("娱乐", "", R.color.purple_200, 0l, 0l, 10f));
        blockInfoList.add(new BlockInfo("", "", R.color.white2, 0l, 0l, 6f));
        return blockInfoList;
    }

    public static void setBlockInfoList(List<BlockInfo> blockInfoList) {
        GlobalVariable.blockInfoList = blockInfoList;
    }
}
