package com.zhiwan.hamitao.zhiwan.mvp.music;

import com.zhiwan.hamitao.zhiwan.base.BaseView;

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





}
