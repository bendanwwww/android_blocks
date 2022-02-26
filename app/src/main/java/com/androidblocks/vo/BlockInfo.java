package com.androidblocks.vo;

import com.androidblocks.db.ColumnName;
import com.androidblocks.db.PrimaryKey;

/**
 * 区间信息
 *
 * @author lsy
 */
public class BlockInfo {
    /** id */
    @PrimaryKey(name = "id")
    private String id;
    /** 区间生效类型 */
    @ColumnName(name = "effect_type")
    private EffectType effectType;
    /** 描述文字 */
    @ColumnName(name = "text")
    private String text;
    /** 详细信息 */
    @ColumnName(name = "info")
    private String info;
    /** 区间颜色 */
    @ColumnName(name = "color")
    private int color;
    /** 开始时间 */
    @ColumnName(name = "start_hour")
    private String startHour;
    /** 结束时间 */
    @ColumnName(name = "end_hour")
    private String endHour;
    /** 开始日期时间戳 */
    @ColumnName(name = "start_time")
    private long startTime;
    /** 结束日期时间戳 */
    @ColumnName(name = "end_time")
    private long endTime;
    /** 占比 */
    @ColumnName(name = "proportions")
    private float proportions;
    /** 是否为空闲区间 */
    @ColumnName(name = "idle")
    private boolean idle = false;
    /** 删除标识 (0: 正常 1: 删除) */
    @ColumnName(name = "delete_state")
    private int deleteState;

    public BlockInfo() {}

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, float proportions, boolean idle) {
        this(id, effectType, text, info, color, startHour, endHour, 0L, 0L, proportions, idle, 0);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, long startTime, long endTime, float proportions, boolean idle) {
        this(id, effectType, text, info, color, "", "", startTime, endTime, proportions, idle, 0);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, float proportions) {
        this(id, effectType, text, info, color, startHour, endHour, 0L, 0L, proportions, false, 0);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, long startTime, long endTime, float proportions) {
        this(id, effectType, text, info, color, "", "", startTime, endTime, proportions, false, 0);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, long startTime, long endTime, float proportions, boolean idle, int deleteState) {
        this.id = id;
        this.effectType = effectType;
        this.text = text;
        this.info = info;
        this.color = color;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startTime = startTime;
        this.endTime = endTime;
        this.proportions = proportions;
        this.idle = idle;
        this.deleteState = deleteState;
    }

    /**
     * 更新对象 若无更新则返回false
     * @return
     */
    public boolean updateObject(String text, String info, int color) {
        boolean res = false;
        if (!this.text.equals(text)) {
            this.text = text;
            res = true;
        }
        if (!this.info.equals(info)) {
            this.info = info;
            res = true;
        }
        if (this.color != color) {
            this.color = color;
            res = true;
        }
        return res;
    }

    /**
     * 更新对象 若无更新则返回false
     * @return
     */
    public boolean updateObject(String text, String info, int color, String startHour, String endHour) {
        boolean res = false;
        if (!this.startHour.equals(startHour)) {
            this.startHour = startHour;
            res = true;
        }
        if (!this.endHour.equals(endHour)) {
            this.endHour = endHour;
            res = true;
        }
        return res || updateObject(text, info, color);
    }

    /**
     * 更新对象 若无更新则返回false
     * @return
     */
    public boolean updateObject(String text, String info, int color, long startTime, long endTime) {
        boolean res = false;
        if (this.startTime != startTime) {
            this.startTime = startTime;
            res = true;
        }
        if (this.endTime != endTime) {
            this.endTime = endTime;
            res = true;
        }
        return res || updateObject(text, info, color);
    }

    public enum EffectType {
        WORK_DAY,
        WEEKEND_DAY,
        FIXED_DAY
        ;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public float getProportions() {
        return proportions;
    }

    public void setProportions(float proportions) {
        this.proportions = proportions;
    }

    public boolean getIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }
}
