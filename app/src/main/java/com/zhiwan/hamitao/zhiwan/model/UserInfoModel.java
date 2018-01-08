package com.zhiwan.hamitao.zhiwan.model;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class UserInfoModel {


    /**
     * 用户ID
     */
    private long userId;

    /**
     * 昵称
     */
    private String nick;


    /**
     * 头像
     */
    private String face;

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
}
