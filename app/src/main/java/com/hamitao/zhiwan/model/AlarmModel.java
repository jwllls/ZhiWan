package com.hamitao.zhiwan.model;

import java.io.Serializable;

/**
 * Created by linjianwen on 2018/2/3.
 */

public class AlarmModel implements Serializable {


    /**
     * 是否启用闹钟
     */
    private boolean isOpen;

    /**
     * 闹钟时间
     */
    private String time;

    /**
     * 闹钟备注
     */
    private String label;

    /**
     * 闹钟周期
     */
    private String cycle;

    /**
     * 铃声
     */
    private String bellStr;

    /**
     * 录音铃声
     */
    private String recordBellStr;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getBellStr() {
        return bellStr;
    }

    public void setBellStr(String bellStr) {
        this.bellStr = bellStr;
    }

    public String getRecordBellStr() {
        return recordBellStr;
    }

    public void setRecordBellStr(String recordBellStr) {
        this.recordBellStr = recordBellStr;
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "isOpen=" + isOpen +
                ", time='" + time + '\'' +
                ", label='" + label + '\'' +
                ", cycle='" + cycle + '\'' +
                ", bellStr='" + bellStr + '\'' +
                ", recordBellStr='" + recordBellStr + '\'' +
                '}';
    }
}
