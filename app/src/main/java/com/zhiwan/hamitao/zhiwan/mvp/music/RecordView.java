package com.zhiwan.hamitao.zhiwan.mvp.music;

import com.zhiwan.hamitao.zhiwan.base.BaseView;
import com.zhiwan.hamitao.zhiwan.model.RecordFileModel;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/9.
 */

public interface RecordView extends BaseView{

    /**
     * 开始录音
     */
    void startRecort();

    /**
     * 保存录音
     */
    void savaRecort();

    /**
     * 重新录音
     */
    void reRecord();

    /**
     * 复位
     */
    void reset();

    /**
     * 获取录音列表
     */
    void getRecordList(List<RecordFileModel> list);





}
