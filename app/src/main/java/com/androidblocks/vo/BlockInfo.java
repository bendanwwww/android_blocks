package com.androidblocks.vo;

/**
 * 区间信息
 *
 * @author lsy
 */
public class BlockInfo {

    /** 描述文字 */
    private String text;
    /** 详细信息 */
    private String info;
    /** 区间颜色 */
    private int color;
    /** 开始时间 */
    private long startTime;
    /** 结束时间 */
    private long endTime;
    /** 占比 */
    private float proportions;
    /** 是否为空闲区间 */
    private boolean idle = false;

    public BlockInfo() {}

    public BlockInfo(String text, String info, int color, long startTime, long endTime, float proportions) {
        this(text, info, color, startTime, endTime, proportions, false);
    }

    public BlockInfo(String text, String info, int color, long startTime, long endTime, float proportions, boolean idle) {
        this.text = text;
        this.info = info;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.proportions = proportions;
        this.idle = idle;
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
}
