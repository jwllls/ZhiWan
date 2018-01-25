package com.zhiwan.hamitao.im_module.chat.views.voice_record;

import android.media.MediaRecorder;
import android.util.Log;

import com.langu.frame.Constant;

import java.io.File;
import java.io.IOException;

public class MyAudioRecorder implements RecordStrategy {
    private static int SAMPLE_RATE_IN_HZ = 8000;
    private MediaRecorder recorder;

    private File voiceFile = null;
    private boolean isRecording = false;

    private boolean isCreateNewPath = true;

    public MyAudioRecorder() {
        voiceFile = createVoiceFile();
    }

    public MyAudioRecorder(String path, boolean isCreateNewPath) {
        this.isCreateNewPath = isCreateNewPath;
        voiceFile = new File(path);
        ready();
    }

    @Override
    public void ready() {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.i("ke", "SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(voiceFile.getAbsolutePath()).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.i("ke", "Path to file could not be created");
        }

        // 生成新路径
        if (isCreateNewPath) {
            voiceFile = createVoiceFile();
        }
        recorder = new MediaRecorder();
        recorder.setOutputFile(voiceFile.getAbsolutePath());
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置MediaRecorder的音频源为麦克风
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);// 设置MediaRecorder录制的音频格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置MediaRecorder录制音频的编码为amr
        recorder.setAudioEncodingBitRate(44100);
//		recorder.setAudioSamplingRate(44100);
//		recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);// 采样率
    }

    @Override
    public void start() {
        if (!isRecording) {
            try {
                recorder.prepare();
                recorder.start();
                isRecording = true;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        if (recorder != null) {
            // 在结束录制的时候必须设置为null,否则必崩
            recorder.setOnInfoListener(null);
            recorder.setOnErrorListener(null);
            recorder.setPreviewDisplay(null);
            if (isRecording) {
                try {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    isRecording = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteOldFile() {
        if (voiceFile != null) {
            voiceFile.deleteOnExit();
        }
    }

    @Override
    public double getAmplitude() {
        if (!isRecording) {
            return 0;
        }
        try {
            return recorder.getMaxAmplitude();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getFilePath() {
        if (voiceFile != null) {
            return voiceFile.getAbsolutePath();
        } else {
            return "";
        }
    }

    public static File createVoiceFile() {
        File audioRecordFile = new File(Constant.USER_VOICE_LOCAL);
        if (!audioRecordFile.exists()) {
            audioRecordFile.mkdir();
        }
        Log.d("VoiceUtil", "filePath:" + Constant.USER_VOICE_LOCAL + System.currentTimeMillis() + ".amr");
        File tmpFile = new File(Constant.USER_VOICE_LOCAL + System.currentTimeMillis() + ".amr");
        return tmpFile;
    }
}