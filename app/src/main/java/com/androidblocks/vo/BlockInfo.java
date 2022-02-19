package com.androidblocks.vo;

/**
 * 区间信息
 *
 * @author lsy
 */
public class BlockInfo {

    /** id */
    private String id;
    /** 区间生效类型 */
    private EffectType effectType;
    /** 描述文字 */
    private String text;
    /** 详细信息 */
    private String info;
    /** 区间颜色 */
    private int color;
    /** 开始时间 */
    private String startHour;
    /** 结束时间 */
    private String endHour;
    /** 开始日期时间戳 */
    private long startTime;
    /** 结束日期时间戳 */
    private long endTime;
    /** 占比 */
    private float proportions;
    /** 是否为空闲区间 */
    private boolean idle = false;

    public BlockInfo() {}

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, float proportions, boolean idle) {
        this(id, effectType, text, info, color, startHour, endHour, 0L, 0L, proportions, idle);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, long startTime, long endTime, float proportions, boolean idle) {
        this(id, effectType, text, info, color, "", "", startTime, endTime, proportions, idle);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, float proportions) {
        this(id, effectType, text, info, color, startHour, endHour, 0L, 0L, proportions, false);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, long startTime, long endTime, float proportions) {
        this(id, effectType, text, info, color, "", "", startTime, endTime, proportions, false);
    }

    public BlockInfo(String id, EffectType effectType, String text, String info, int color, String startHour, String endHour, long startTime, long endTime, float proportions, boolean idle) {
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

    public boolean isIdle() {
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
}
