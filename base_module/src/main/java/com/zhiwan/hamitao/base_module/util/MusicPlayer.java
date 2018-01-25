package com.zhiwan.hamitao.base_module.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zenfer on 2017/1/12.
 */
public class MusicPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    private Context context;
    private MediaPlayer mediaPlayer; // 媒体播放器
    private SeekBar seekBar; // 拖动条

    private TimerTask timerTask;
    private Timer mTimer;

    private boolean hasPrepared = false;

    public String getUrl() {
        return url;
    }

    private String url = "";

    // 初始化播放器
    public MusicPlayer(Context context, SeekBar seekBar) {
        super();
        this.context = context;
        this.seekBar = seekBar;
//         每一秒触发一次
        timerTask = new TimerTask() {

            @Override
            public void run() {
                if (mediaPlayer == null)
                    return;
                if (mediaPlayer.isPlaying()) {
                    handler.sendEmptyMessage(0); // 发送消息
                }
            }
        };
        mTimer = new Timer();
        mTimer.schedule(timerTask, 0, 1000);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                if (seekBar != null) {
                    long pos = seekBar.getMax() * position / duration;
                    seekBar.setProgress((int) pos);
                }
            }
        }
    };

    private AnimationDrawable frameAnimatio;

    /**
     * @param url           url地址
     * @param frameAnimatio 动画
     */
    public void playUrl(String url, AnimationDrawable frameAnimatio) {
        this.frameAnimatio = frameAnimatio;
        this.url = url;
        try {
            hasPrepared = false;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    // 暂停
    public void pause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    public void play() {
        if (mediaPlayer != null && hasPrepared)
            mediaPlayer.start();
    }

    /**
     * 是否在播放中
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    // 停止
    public void stop() {
        url = "";
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (onMusicListener != null) {
            onMusicListener.stoped();
        }
        frameAnimatio.stop();
        frameAnimatio.selectDrawable(0);
    }

    // 播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true;
        if (onMusicListener != null) {
            onMusicListener.onPrepared(mp);
        }
        frameAnimatio.start();
//        mp.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    // 播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("mediaPlayer", "onCompletion");
        if (seekBar != null) {
            seekBar.setProgress(0);
        }
        if (onMusicListener != null) {
            onMusicListener.onCompletion(mp);
        }
        frameAnimatio.stop();
        frameAnimatio.selectDrawable(0);

    }

    /**
     * 缓冲更新
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (seekBar != null) {
            seekBar.setSecondaryProgress(percent);
            int currentProgress = seekBar.getMax()
                    * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
            Log.e(currentProgress + "% play", percent + " buffer");
        }
        if (onMusicListener != null) {
            onMusicListener.onBufferingUpdate(mp, percent);
        }
    }

    public void setOnMusicListener(OnMusicListener onMusicListener) {
        this.onMusicListener = onMusicListener;
    }

    private OnMusicListener onMusicListener = null;

    public interface OnMusicListener {
        void onPrepared(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);

        void onBufferingUpdate(MediaPlayer mp, int percent);

        void stoped();
    }
}
