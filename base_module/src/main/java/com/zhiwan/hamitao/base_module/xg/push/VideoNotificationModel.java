package com.zhiwan.hamitao.base_module.xg.push;

import java.io.Serializable;

/**
 * 离线通知资料
 *
 * @author lian
 */
public class VideoNotificationModel implements Serializable {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 用户的昵称
     */
    private String nick;

    /**
     * 用户的头像
     */
    private String face;

    /**
     * 房间号
     */
    private int roomNum;

    /**
     * 环信账户
     */
    private String hxNick;

    public String getHxNick() {
        return hxNick;
    }

    public void setHxNick(String hxNick) {
        this.hxNick = hxNick;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

}
