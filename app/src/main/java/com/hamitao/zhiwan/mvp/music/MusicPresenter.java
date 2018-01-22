package com.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.hamitao.zhiwan.base.BasePresenter;

import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class MusicPresenter implements BasePresenter {

    private MusicView musicView;
    private Context context;
    private MediaPlayer mediaPlayer;



    private SeekBar seekBar;
    private LrcView lrcView;


    public MusicPresenter(MusicView musicView, Context context) {
        this.musicView = musicView;
        this.context = context;
        mediaPlayer = new MediaPlayer();

    }

    public void setObject(SeekBar seekBar, LrcView lrcView) {
        this.seekBar = seekBar;
        this.lrcView = lrcView;
        init();
    }


    @Override
    public void start() {
    }

    private void init() {



    }

    @Override
    public void stop() {
    }







    //开始
    public void playStart() {
        mediaPlayer.start();
    }

    //播放上一首
    public void playBefore() {

    }

    //播放下一首
    public void playNext() {

    }

    //暂停播放
    public void playPause() {

        if (mediaPlayer!=null){
            mediaPlayer.pause();
        }

    }


    //是否在播放


    //停止播放
    public void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //销毁播放器
    public void destoryPlaler() {

    }

}
