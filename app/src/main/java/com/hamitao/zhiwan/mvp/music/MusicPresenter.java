package com.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.hamitao.zhiwan.base.BasePresenter;

import java.io.IOException;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class MusicPresenter implements BasePresenter {

    private MusicView musicView;
    private Context context;
    private MediaPlayer mediaPlayer;

    public MusicPresenter(MusicView musicView, Context context) {
        this.musicView = musicView;
        this.context = context;
        mediaPlayer = new MediaPlayer();
        musicView.getMediaPlayer(mediaPlayer);
    }


    @Override
    public void start() {
        play();
    }

    @Override
    public void stop() {

    }


    /**
     * 播放上一首
     */

    public void palyBefore() {

    }


    /**
     * 播放或暂停
     */
    public void play() {
        mediaPlayer.reset();
//        String musicUrl = "http://res.webftp.bbs.hnol.net/zhangyu/music/cd63/03.mp3";
//        mediaPlayer.start();
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getAssets().openFd("chengdu.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 播放下一首
     */

    public void playNext() {

    }


    //开始
    public void start(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暂停
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    //继续播放
    public void continuePlay() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //停止播放
    public void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //销毁播放器
    public void destortMediaPlaler() {
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
