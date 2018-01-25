package com.zhiwan.hamitao.base_module.model.chat;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ChatSoundDo {

    public ChatSoundDo() {
    }

    public ChatSoundDo(String url, long duration) {
        this.url = url;
        this.duration = duration;
    }

    private String url;

    private long duration;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
