package com.zhiwan.hamitao.xg.push;

import java.io.Serializable;

public class NotificationMsgModel implements Serializable{

    private String msg;

    private long displayTime;

    private long serveUserId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(long displayTime) {
        this.displayTime = displayTime;
    }

    public long getServeUserId() {
        return serveUserId;
    }

    public void setServeUserId(long serveUserId) {
        this.serveUserId = serveUserId;
    }
}
