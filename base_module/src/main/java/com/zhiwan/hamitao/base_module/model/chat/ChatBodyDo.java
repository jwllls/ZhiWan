package com.zhiwan.hamitao.base_module.model.chat;

/**
 * Created by Administrator on 2017/9/1.
 */

public class ChatBodyDo {

    private long duration;

    private String imgUrl;

    private Object content;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
