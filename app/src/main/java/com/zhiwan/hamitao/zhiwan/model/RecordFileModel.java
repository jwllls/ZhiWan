package com.zhiwan.hamitao.zhiwan.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by linjianwen on 2018/1/10.
 */

public class RecordFileModel implements Serializable {

    /**
     * 录音文件
     */
    private File recordFile;

    /**
     * 录音日期
     */
    private String recordDate;

    /**
     * 录音时厂
     */
    private String Duration;


    public File getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(File recordFile) {
        this.recordFile = recordFile;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
