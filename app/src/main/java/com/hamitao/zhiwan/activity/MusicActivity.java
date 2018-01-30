package com.hamitao.zhiwan.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/1/4.
 * 播放器页面
 */

public class MusicActivity extends BaseActivity {


    @BindView(R.id.lrcView)
    LrcView lrcView;   //歌词
    @BindView(R.id.seekbar)
    SeekBar seekBar;  //进度条
    @BindView(R.id.more)
    TextView more;  //歌单
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_playMode)
    TextView tvPlayMode;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.iv_musicState)
    ImageView ivMusicState;

    private MediaPlayer mediaPlayer;

    private String url = "http://www.tingge123.com/mp3/2016-09-15/1473940221.mp3";

    private boolean isPause;

    private Handler handler = new Handler();

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mediaPlayer = new MediaPlayer();
    }


    /**
     * 获取歌词
     * @param fileName
     * @return
     */
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }


    private void initView() {
        more.setVisibility(View.VISIBLE);

        more.setText("歌单");

        lrcView.loadLrc(getLrcText("chengdu.lrc"));

        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
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


    //--------------------------播放器--------------------------------------------

    //播放
    public void playStart(String filePath, MediaPlayer.OnCompletionListener listener) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(listener);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaPlayer.reset();
                return false;
            }
        });
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("读取文件异常：" + e.getMessage());
        }
        mediaPlayer.start();
        isPause = false;
    }

    //暂停
    public void playPause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    // 继续
    public void resume() {
        if (mediaPlayer != null && isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }


    //--------------------------播放器--------------------------------------------


    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.more, R.id.tv_put, R.id.tv_collection, R.id.tv_playMode, R.id.tv_share, R.id.tv_timer, R.id.tv_start, R.id.tv_before, R.id.tv_next, R.id.iv_musicState})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.tv_put:
                break;
            case R.id.tv_collection:
                break;
            case R.id.tv_playMode:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_timer:
                break;
            case R.id.tv_start:
                if (!mediaPlayer.isPlaying()) {
                    if (isPause) {
                        //暂停
                        resume();
                    } else {
                        //非暂停，歌曲首次播放
                        playStart(url, new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                            }
                        });

                    }
                    handler.post(runnable);
                } else {
                    //暂停
                    playPause();
                    handler.removeCallbacks(runnable);
                }
                break;
            case R.id.tv_before:
                break;
            case R.id.tv_next:
                break;
            case R.id.iv_musicState:
                break;
        }
    }
}
