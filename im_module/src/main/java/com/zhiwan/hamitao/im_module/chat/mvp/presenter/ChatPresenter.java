package com.zhiwan.hamitao.im_module.chat.mvp.presenter;

import android.accounts.NetworkErrorException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMMessageDraft;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 聊天界面逻辑
 */
public class ChatPresenter implements Observer {

    private ChatView view;
    private TIMConversation conversation;
    private boolean isGetingMessage = false;
    private static final int LAST_MESSAGE_NUM = 20;
    private final static String TAG = "ChatPresenter";

    public ChatPresenter(ChatView view, String identify, TIMConversationType type) {
        this.view = view;
        conversation = TIMManager.getInstance().getConversation(type, identify);
    }

    //*************************************** 自定义的消息发送 开始 ***************************************//
    /**
     * 根据不同项目的情况各自调整 "自定义的消息发送" 的开始到结束中间部分的模块
     */
    /**
     * 发送文字消息
     *
     * @param user 对方的资料
     * @param text 消息内容
     */
    public void sendTextMessage(UserModel user, String text) {
        ChatSo so = new ChatSo();
        so.setChatUser(UserUtil.user());
        so.setToUser(user);
        so.setContent(text);
        so.setContentType((byte) ChatEnum.TEXT.getType());
        sendMessage(user, so);
    }
    /**
     * 发送订单给客服
     *
     * @param user 对方的资料
     * @param text 订单内容
     */
    public void sendOrderForServerMessage(UserModel user, String text) {
        ChatSo so = new ChatSo();
        so.setChatUser(UserUtil.user());
        so.setToUser(user);
        so.setContent(text);
        so.setContentType((byte) ChatEnum.Order_For_Server.getType());
        sendMessage(user, so);
    }

    /**
     * 发送图片消息
     *
     * @param imgUrl 图片url
     */
    public void sendImgMessage(final UserModel user, final String imgUrl, final int imgWidth, final int imgHeight) {
        final File file = new File(imgUrl);
        if (!file.exists()) {
            view.onSendMessageFail(0, "图片文件不存在", null);
            view.onFinish();
            return;
        }
        OkhttpUploadMultipleFileUtil util = new OkhttpUploadMultipleFileUtil(new OkhttpUploadMultipleFileUtil.OnUploadListener() {
            @Override
            public void onBeforeUpload() {
                view.onBegin();
            }

            @Override
            public void onUploadSuccess(final FileUploadNetWordResult result) {
                if (result != null && result.getResult() != null) {
                    final String pathImages = result.getResult().toString();
                    if (StringUtil.isNotBlank(pathImages)) {
                        ChatBodyDo chatBodyDo = new ChatBodyDo();
                        ChatImgDo chatImgDo = new ChatImgDo();
                        chatImgDo.setImgUrl(pathImages);
                        chatImgDo.setImgHeight(imgHeight);
                        chatImgDo.setImgWidth(imgWidth);
                        chatBodyDo.setContent(chatImgDo);
                        ChatSo so = new ChatSo();
                        so.setChatUser(UserUtil.user());
                        so.setToUser(user);
                        so.setContent(GsonUtil.GsonToString(chatBodyDo));
                        so.setContentType((byte) ChatEnum.IMAGE.getType());
                        sendMessage(user, so);
                    }
                }
            }

            @Override
            public void onUploadFail(final String error) {
                view.onSendMessageFail(0, "上传失败:" + error, null);
            }

            @Override
            public void onAfterUpload() {
                view.onFinish();
            }

            @Override
            public void onProgress(long currentBytes, long contentLength, boolean done) {

            }
        });
        Map<String, File> files = new HashMap<>();
        files.put("file", file);
        Map<String, String> values = new HashMap<>();
        if (UserUtil.user() != null) {
            values.put("fileName", file.getName());
            values.put("userId", UserUtil.user().getUserId() + "");
            values.put("token", UserUtil.user().getToken());
        }
        try {
            util.upload(Constant.PROXY_SERVER_URL + "/api/upload/chat/file", files, values);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送语音消息
     *
     * @param filepath 语音本地路径
     */
    public void sendVoiceMessage(final UserModel user, String filepath, final long duration) {
        final File file = new File(filepath);
        if (file.exists()) {
            OkhttpUploadMultipleFileUtil util = new OkhttpUploadMultipleFileUtil(new OkhttpUploadMultipleFileUtil.OnUploadListener() {
                @Override
                public void onBeforeUpload() {
                    view.onBegin();
                }

                @Override
                public void onUploadSuccess(final FileUploadNetWordResult viewResult) {
                    if (viewResult != null && viewResult.getResult() != null) {
                        String voiceUrl = viewResult.getResult().toString();
                        ChatSo so = new ChatSo();
                        so.setChatUser(UserUtil.user());
                        so.setToUser(user);
                        so.setContent(GsonUtil.GsonToString(new ChatSoundDo(voiceUrl, duration)));
                        so.setContentType((byte) ChatEnum.VOICE.getType());
                        sendMessage(user, so);
                    }
                }

                @Override
                public void onUploadFail(final String error) {
                    view.onSendMessageFail(0, "上传失败:" + error, null);
                }

                @Override
                public void onAfterUpload() {
                    view.onFinish();
                }

                @Override
                public void onProgress(long currentBytes, long contentLength, boolean done) {

                }
            });
            Map<String, File> files = new HashMap<>();
            files.put("file", file);
            Map<String, String> values = new HashMap<>();
            if (UserUtil.user() != null) {
                values.put("fileName", file.getName());
                values.put("userId", UserUtil.user().getUserId() + "");
                values.put("token", UserUtil.user().getToken());
            }
            try {
                util.upload(Constant.PROXY_SERVER_URL + "/api/upload/chat/file", files, values);
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        } else {
            view.onSendMessageFail(0, "上传失败:语音文件不存在", null);
        }
    }

    /**
     * 发送消息
     *
     * @param so ChatSo
     */
    private void sendMessage(UserModel user, final ChatSo so) {
        if (user != null)
            NetWorkRequest.sendMessage(user.getUserId(), so.getContentType(), so.getContent().toString(), user.getImId().equals(ChatConstant.ID_Server), new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
                @Override
                public void onSuccess(NetWordResult result) {
                    final TIMMessage message = IMHelper.getInstance().getMessage(GsonUtil.GsonToString(so));
                    sendMessageBySDK(message);
                }

                @Override
                public void onFail(NetWordResult result, String msg) {
                    view.onSendMessageFail(0, msg, null);
                }

                @Override
                public void onBegin() {
                    view.onBegin();
                }

                @Override
                public void onEnd() {
                    view.onFinish();
                }
            }));
    }
    //*************************************** 自定义的消息发送 结束 ***************************************//


    /**
     * 加载页面逻辑
     */
    public void start() {
        //注册消息监听
        IMMessageListener.getInstance().addObserver(this);
//        RefreshEvent.getInstance().addObserver(this);
        getMessage(null, false);
        if (conversation.hasDraft()) {
            view.showDraft(conversation.getDraft());
        }
    }

    /**
     * 中止页面逻辑
     */
    public void stop() {
        //注销消息监听
        IMMessageListener.getInstance().deleteObserver(this);
//        RefreshEvent.getInstance().deleteObserver(this);
    }

    /**
     * 获取聊天TIM会话
     */
    public TIMConversation getConversation() {
        return conversation;
    }

    /**
     * 发送消息
     *
     * @param message 发送的消息
     */
    public void sendMessageBySDK(final TIMMessage message) {
        conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code含义请参见错误码表
                view.onSendMessageFail(code, desc, message);
            }

            @Override
            public void onSuccess(TIMMessage msg) {
                //发送消息成功,消息状态已在sdk中修改，此时只需更新界面
                view.onSendMessageSuccess(msg);
                IMMessageListener.getInstance().onNewMessage(null);

            }
        });
        //message对象为发送中状态
        IMMessageListener.getInstance().onNewMessage(message);
    }

    /**
     * 保存消息到本地数据库
     *
     * @param message 消息体
     */
    public void addMessage(TIMMessage message) {
        UserModel model = UserUtil.user();
        if (model == null) {
            view.onSendMessageFail(0, "本地用户信息为空!", message);
            return;
        }
        conversation.saveMessage(message, String.valueOf(model.getImId()), true);
        view.showMessage(message);
    }


    /**
     * 发送在线消息
     *
     * @param message 发送的消息
     */
    public void sendOnlineMessage(final TIMMessage message) {
        conversation.sendOnlineMessage(message, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                view.onSendMessageFail(i, s, message);
            }

            @Override
            public void onSuccess(TIMMessage message) {

            }
        });
    }


    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof IMMessageListener) {
            TIMMessage msg = (TIMMessage) data;
            if (msg != null && msg.getConversation().getPeer().equals(conversation.getPeer()) && msg.getConversation().getType() == conversation.getType()) {
                view.showMessage(msg);
            }
        }
//        else if (observable instanceof RefreshEvent){
//            view.clearAllMessage();
//            getMessage(null);
//        }
    }


    /**
     * 获取消息
     *
     * @param message 最后一条消息
     */
    public void getMessage(@Nullable TIMMessage message, final boolean isLoadMore) {
        if (!isGetingMessage) {
            isGetingMessage = true;
            conversation.getLocalMessage(LAST_MESSAGE_NUM, message, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    isGetingMessage = false;
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    isGetingMessage = false;
                    view.showMessage(timMessages, isLoadMore);
                }
            });
        }

    }

    /**
     * 设置会话为已读
     */
    public void readMessages() {
        conversation.setReadMessage();
    }


    /**
     * 保存草稿
     *
     * @param message 消息数据
     */
    public void saveDraft(TIMMessage message) {
        conversation.setDraft(null);
        if (message != null && message.getElementCount() > 0) {
            TIMMessageDraft draft = new TIMMessageDraft();
            for (int i = 0; i < message.getElementCount(); ++i) {
                draft.addElem(message.getElement(i));
            }
            conversation.setDraft(draft);
        }
    }

}
