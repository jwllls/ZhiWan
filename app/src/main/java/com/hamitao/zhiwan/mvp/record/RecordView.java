package com.hamitao.zhiwan.mvp.record;


import com.hamitao.zhiwan.base.BaseView;
import com.hamitao.zhiwan.model.RecordModel;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/9.
 */

public interface RecordView extends BaseView {

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
    void getRecordList(List<RecordModel> list);

    /**
     *  显示重录、保存 按钮
     */
    void showButton();

    /**
     *  隐藏重录、保存 按钮
     */
    void hideButton();



}
