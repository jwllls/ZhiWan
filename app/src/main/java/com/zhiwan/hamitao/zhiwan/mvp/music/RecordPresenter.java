package com.zhiwan.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import com.zhiwan.hamitao.zhiwan.Constant;
import com.zhiwan.hamitao.zhiwan.base.BasePresenter;

import java.io.File;
import java.io.IOException;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView recordView;
    private Context context;

    private MediaRecorder mediaRecorder;

    private String filePath = Constant.USER_RECORD_LOCAL;


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


    private void initRecorder() {
        if (mediaRecorder == null) {
            File dir = new File(Environment.getExternalStorageDirectory(), "sounds");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File soundFile = new File(dir, System.currentTimeMillis() + ".aac");

            if (!soundFile.exists()) {
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //声源
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);//输出格式 ACC
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
            // mediaRecorder.setAudioEncodingBitRate();//编码位率
            // mediaRecorder.setAudioSamplingRate(); //采样率
            mediaRecorder.setOutputFile(soundFile.getAbsolutePath());


        }
    }


    /**
     * 开始录音
     */
    public void startRecord() {
        initRecorder();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存录音
     */
    public void saveRecord() {

    }

    /**
     * 结束录音
     */
    public void stopRecord() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

}
