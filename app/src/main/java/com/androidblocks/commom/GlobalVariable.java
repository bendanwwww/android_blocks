package com.androidblocks.commom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.androidblocks.blocks.R;
import com.androidblocks.enums.ActivityEnum;
import com.androidblocks.enums.ViewEnum;
import com.androidblocks.utils.DateUtils;
import com.androidblocks.utils.TextUtils;
import com.androidblocks.vo.BlockInfo;

import android.app.Activity;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 全局参数
 *
 * @author lsy
 */
public class GlobalVariable {

    /** 画布集合 */
    private static volatile Map<ViewEnum, View> viewMap = new HashMap<>();
    /** 页面集合 */
    private static volatile Map<ActivityEnum, Activity> activityMap = new HashMap<>();
    /** 日程区间列表 */
    private static volatile Map<String, BlockInfo> blockInfoMap = new HashMap<>();
    /** 日程是否被更新 */
    private static volatile boolean blockInfoUpdate = false;
    /** 日程锁 */
    private static final Object BLOC_LOCK = new Object();

    public static List<BlockInfo> getAllBlockInfoList() {
        return new ArrayList<>(blockInfoMap.values()).stream()
                .collect(Collectors.toList());
    }

    public static List<BlockInfo> getBlockInfoList() {
        return new ArrayList<>(blockInfoMap.values()).stream()
                .filter(b -> b.getDeleteState() == 0)
                .collect(Collectors.toList());
    }

    public static List<BlockInfo> getBlockInfoList(BlockInfo.EffectType effectType) {
        List<BlockInfo> blockInfoList = new ArrayList<>(blockInfoMap.values());
        List<BlockInfo> resList = blockInfoList.stream()
                .filter(b -> b.getEffectType().equals(effectType))
                .filter(b -> b.getDeleteState() == 0)
                .sorted((b1, b2) -> DateUtils.afterHour(b1.getStartHour(), b2.getStartHour()) ? -1 : 1)
                .map(b -> {
                    b.setStartTime(DateUtils.getHourTimestamp(b.getStartHour()));
                    b.setEndTime(DateUtils.getHourTimestamp(b.getEndHour()));
                    return b;
                })
                .collect(Collectors.toList());
        // 填充空闲时间
        List<BlockInfo> idleList = new ArrayList<>();
        // 若无日程 或第一个区间未从一天开始时刻开始 补充空闲区间
        if (CollectionUtils.isEmpty(resList) || DateUtils.afterHour(DateUtils.getFirstHourByDay(), resList.get(0).getStartHour())) {
            String endHour = "";
            if (CollectionUtils.isEmpty(resList)) {
                endHour = DateUtils.getEndHourByDay();
            } else {
                endHour = resList.get(0).getStartHour();
            }
            idleList.add(new BlockInfo(TextUtils.initId(), effectType, "", "", R.color.white2, DateUtils.getFirstHourByDay(), endHour, 0f, true));
        }
        for (int i = 0 ; i < resList.size(); i++) {
            if (i + 1 == resList.size()) {
                // 若最后一个区间未到一天结束时刻 补充空闲区间
                if (DateUtils.afterHour(resList.get(i).getEndHour(), DateUtils.getEndHourByDay())) {
                    idleList.add(new BlockInfo(TextUtils.initId(), effectType, "", "", R.color.white2, resList.get(i).getEndHour(), DateUtils.getEndHourByDay(), 0f, true));
                }
                break;
            }
            BlockInfo firstBlock = resList.get(i);
            BlockInfo nextBlock = resList.get(i + 1);
            // 若上一段时间和下一段时间存在间隙 则添加区间
            if (firstBlock.getEndTime() < nextBlock.getStartTime()) {
                idleList.add(new BlockInfo(TextUtils.initId(), effectType, "", "", R.color.white2, firstBlock.getEndTime(), nextBlock.getStartTime(), 0f, true));
            }
        }
        resList.addAll(idleList);
        // 重新排序并计算比例
        resList = resList.stream()
                .map(b -> {
                    // 若时间缺失 则补充一下
                    if (b.getStartTime() == 0L) {
                        b.setStartTime(DateUtils.getHourTimestamp(b.getStartHour()));
                    }
                    if (b.getEndTime() == 0L) {
                        b.setEndTime(DateUtils.getHourTimestamp(b.getEndHour()));
                    }
                    if (StringUtils.isEmpty(b.getStartHour())) {
                        b.setStartHour(DateUtils.getTimestampToHour(b.getStartTime()));
                    }
                    if (StringUtils.isEmpty(b.getEndHour())) {
                        b.setEndHour(DateUtils.getTimestampToHour(b.getEndTime()));
                    }
                    b.setProportions(b.getEndTime() - b.getStartTime());
                    return b;
                })
                .sorted((b1, b2) -> DateUtils.afterHour(b1.getStartHour(), b2.getStartHour()) ? -1 : 1)
                .collect(Collectors.toList());
        return resList;
    }

    public static void delBlockInfoById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        if (blockInfoMap.containsKey(id)) {
            blockInfoMap.get(id).setDeleteState(1);
        }
        setBlockInfoUpdate(true);
    }

    public static BlockInfo getBlockInfoById(String id) {
        return GlobalVariable.blockInfoMap.get(id);
    }

    public static void setBlockInfo(BlockInfo... blockInfos) {
        for (BlockInfo blockInfo : blockInfos) {
            GlobalVariable.blockInfoMap.put(blockInfo.getId(), blockInfo);
        }
        setBlockInfoUpdate(true);
    }

    public static boolean getBlockInfoUpdate() {
        synchronized (BLOC_LOCK) {
            return blockInfoUpdate;
        }
    }

    public static void setBlockInfoUpdate(boolean blockInfoUpdate) {
        synchronized (BLOC_LOCK) {
            GlobalVariable.blockInfoUpdate = blockInfoUpdate;
        }
    }

    public static View getView(ViewEnum key) {
        return viewMap.get(key);
    }

    public static void setView(ViewEnum key, View view) {
        viewMap.put(key, view);
    }

    public static Activity getActivity(ActivityEnum key) {
        return activityMap.get(key);
    }

    public static void setActivityMap(ActivityEnum key, Activity activity) {
        activityMap.put(key, activity);
    }

    public static void closeAllActivity() {
        activityMap.values().stream().forEach(a -> a.finish());
        activityMap.clear();
    }
}
