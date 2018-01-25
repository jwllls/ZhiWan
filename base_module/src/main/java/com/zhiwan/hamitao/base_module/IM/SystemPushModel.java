package com.zhiwan.hamitao.base_module.IM;


import com.zhiwan.hamitao.base_module.model.UserModel;

import java.io.Serializable;

/**
 * Created by tulip on 2017/3/8.
 */

public class SystemPushModel implements Serializable {

    private boolean follow;

    private int roomNum;

    private byte socketType;

    private UserModel user;

    private String content;

    private long mid;

    private String message;
    /**
     * 声网channel key
     */
    private String channelKey;



    /**
     * 声网Recording key
     */
    private String recordKey;

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public byte getSocketType() {
        return socketType;
    }

    public void setSocketType(byte socketType) {
        this.socketType = socketType;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

}
