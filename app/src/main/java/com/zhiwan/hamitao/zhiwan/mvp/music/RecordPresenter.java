package com.zhiwan.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.media.MediaRecorder;

import com.zhiwan.hamitao.zhiwan.base.BasePresenter;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView recordView;
    private Context context;

    private MediaRecorder mediaRecorder;

    public RecordPresenter(RecordView recordView, Context context) {
        this.recordView = recordView;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    private void initRecorder(){
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);

    }


    /**
     * 开始录音
     */
    public void startRecord(){

    }

    /**
     * 保存录音
     */
    public void saveRecord(){

    }

    /**
     * 结束录音
     */
    public void stopRecord(){

    }

}
