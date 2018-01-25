package com.zhiwan.hamitao.base_module.model.chat;

/**
 * 客服消息结构体
 * Created by Ben on 2017/11/16.
 */
public class ChatCoustomDo {

    /**
     * 文案：客服xxx为您服务
     */
    private String text;
    /**
     * 客服的imId
     */
    private long userId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
