package com.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import com.hamitao.zhiwan.base.BasePresenter;

import java.io.IOException;

import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class MusicPresenter implements BasePresenter {

    private MusicView musicView;
    private Context context;
    private MediaPlayer mediaPlayer;

    private Handler handler = new Handler();

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

        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    playStart();
                    handler.post(runnable);
                }
                return true;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                seekBar.setProgress(0);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                lrcView.updateTime(0);
                seekBar.setProgress(0);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });

    }

    @Override
    public void stop() {
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                seekBar.setProgress((int) time);
            }
            handler.postDelayed(this, 300);
        }
    };


    //设置播放路径
    public void setMusicData(String music) {
        try {
            mediaPlayer.setDataSource(music);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    //停止播放
    public void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //销毁播放器
    public void destoryPlaler() {
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
