package com.zhiwan.hamitao.base_module.model.chat;


import com.tencent.imsdk.TIMMessageStatus;
import com.zhiwan.hamitao.base_module.model.UserModel;

/**
 * Created by tulip on 2017/3/6.
 */

public class ChatSo {

    private boolean hasUpdateInfo = false;
    /**
     * 消息类型 {@link  com.zhiwan.hamitao.base_module.enums.ChatEnum }
     */
    private int contentType;
    /**
     * 消息内容
     */
    private Object content;

    /**
     * 消息发送者的资料
     */
    private UserModel chatUser;
    /**
     * 消息接收者的资料
     */
    private UserModel toUser;

    private TIMMessageStatus status = TIMMessageStatus.Sending;

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public UserModel getChatUser() {
        return chatUser;
    }

    public void setChatUser(UserModel chatUser) {
        this.chatUser = chatUser;
    }

    public UserModel getToUser() {
        return toUser;
    }

    public void setToUser(UserModel toUser) {
        this.toUser = toUser;
    }

    public TIMMessageStatus getStatus() {
        return status;
    }

    public void setStatus(TIMMessageStatus status) {
        this.status = status;
    }

    public boolean isHasUpdateInfo() {
        return hasUpdateInfo;
    }

    public void setHasUpdateInfo(boolean hasUpdateInfo) {
        this.hasUpdateInfo = hasUpdateInfo;
    }
}
