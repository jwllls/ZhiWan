package com.zhiwan.hamitao.base_module.IM;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMFaceElem;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUser;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.zhiwan.hamitao.base_module.IM.listener.IMMessageListener;
import com.zhiwan.hamitao.base_module.IM.listener.IMUserStatusListener;
import com.zhiwan.hamitao.base_module.R;
import com.zhiwan.hamitao.base_module.base.BaseApplication;
import com.zhiwan.hamitao.base_module.util.LogUtil;

import java.util.List;

/**
 * Created by Zenfer on 2016/10/11.
 * im
 */
public class IMHelper {

    private String tag = IMHelper.class.getSimpleName();

    private Context context = BaseApplication.getInstance();

    private static IMHelper imHelper = new IMHelper();

    public static IMHelper getInstance() {
        return imHelper;
    }

    public void initTencentIM() {
        if (MsfSdkUtils.isMainProcess(context)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(BaseApplication.getInstance(), R.drawable.app_logo);
                    }
                }
            });
        }

        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(1400059977)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");
        //初始化SDK
        TIMManager.getInstance().init(context, config);

        initIMuserConfig();


        setMessageListener();
    }







    private void initIMuserConfig() {








        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段.setGroupSettings(initGroupSettings())
                //设置资料关系链拉取字段 .setFriendshipSettings(initFriendshipSettings())
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        Log.i(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(tag, "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i(tag, "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

        //消息扩展用户配置
        userConfig = new TIMUserConfigMsgExt(userConfig)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true);

        //资料关系链扩展用户配置
        userConfig = new TIMUserConfigSnsExt(userConfig)
                //开启资料关系链本地存储
                .enableFriendshipStorage(true)
                //设置关系链变更事件监听器
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        Log.i(tag, "OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        Log.i(tag, "OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        Log.i(tag, "OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        Log.i(tag, "OnAddFriendReqs");
                    }

                });

        //群组管理扩展用户配置
        userConfig = new TIMUserConfigGroupExt(userConfig)
                //开启群组资料本地存储
                .enableGroupStorage(true)
                //设置群组资料变更事件监听器
                .setGroupAssistantListener(new TIMGroupAssistantListener() {
                    @Override
                    public void onMemberJoin(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberJoin");
                    }

                    @Override
                    public void onMemberQuit(String groupId, List<String> members) {
                        Log.i(tag, "onMemberQuit");
                    }

                    @Override
                    public void onMemberUpdate(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberUpdate");
                    }

                    @Override
                    public void onGroupAdd(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupAdd");
                    }

                    @Override
                    public void onGroupDelete(String groupId) {
                        Log.i(tag, "onGroupDelete");
                    }

                    @Override
                    public void onGroupUpdate(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupUpdate");
                    }
                });

        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);


        //设置消息监听器，收到新消息时，通过此监听器回调
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                //消息的内容解析请参考消息收发文档中的消息解析说明
                return true; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });

    }

    /**
     * 设置消息监听
     */
    private void setMessageListener() {
        //新消息监听
        IMMessageListener.getInstance();
        //刷新监听
        IMUserStatusListener.getInstance();
        //群组监听
//        TIMManager.getInstance().setGroupAssistantListener(new IMGroupAssistantListener());
        //大群组监听
//        TIMManager.getInstance().setGroupEventListener(new TIMGroupEventListener() {
//            @Override
//            public void onGroupTipsEvent(TIMGroupTipsElem timGroupTipsElem) {
//                LogUtil.d(timGroupTipsElem.getOpUser() + "");
//                Toast.makeText(BaseApplication.getInstance()
//                        , "群时间" + timGroupTipsElem.getOpUser() + "--" + timGroupTipsElem.getType().name()
//                        , Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 登录imsdk
     *
     * @param identifier 用户id
     * @param userSig    用户签名
     */
    public void login(final String identifier, final String userSig, final int sdkAppId, final int accountType, final IMLoginCallBack callBack) {

        TIMUser user = new TIMUser();
        user.setAccountType(accountType + "");
        user.setAppIdAt3rd(sdkAppId + "");
        user.setIdentifier(identifier);

        //发起登录请求
        TIMManager.getInstance().login(identifier, userSig, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                if (callBack != null) callBack.onError(i, s);
            }

            @Override
            public void onSuccess() {
                if (callBack != null) callBack.onSuccess();
            }
        });//登录回调

    }

    /**
     * 登出imsdk
     */
    public void logout() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(BaseApplication.getInstance(), "注销失败：" + i + " msg " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                LogUtil.d(tag, "IMLogout succ !");
            }
        });
    }

    /**
     * 发送文本消息
     *
     * @param text
     */
    public TIMMessage getMessage(String text) {
        TIMMessage msg = new TIMMessage();

        //添加文本内容
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(text.getBytes());

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            LogUtil.d(tag, "addElement failed");
            return null;
        }
        //发送消息
        return msg;
    }

    /**
     * 发送图片消息
     *
     * @param conversation
     * @param path
     */
    public void sendImageMessage(TIMConversation conversation, String path) {
        TIMMessage msg = new TIMMessage();

        //添加图片
        TIMImageElem elem = new TIMImageElem();
        elem.setPath(path);

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            LogUtil.d(tag, "addElement failed");
            return;
        }
        //发送消息
        sendMessage(conversation, msg);
    }

    public void sendFaceMessage(TIMConversation conversation, byte[] data) {
        TIMMessage msg = new TIMMessage();

        //添加表情
        TIMFaceElem elem = new TIMFaceElem();
        elem.setData(data); //自定义byte[]
        elem.setIndex(10);   //自定义表情索引

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            LogUtil.d(tag, "addElement failed");
            return;
        }
        //发送消息
        sendMessage(conversation, msg);
    }

    /**
     * 发送消息
     *
     * @param conversation
     * @param msg
     */
    public void sendMessage(TIMConversation conversation, TIMMessage msg) {
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code含义请参见错误码表
                LogUtil.d(tag, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                LogUtil.d(tag, "SendMsg ok");
            }
        });
    }

    /**
     * 获取自定义参数中的data字符串
     *
     * @param customElem
     * @return
     */
    public static String getMessageContent(TIMCustomElem customElem) {
        if (null == customElem) return "";
        String data = new String(customElem.getData());
        return data;
    }

    public interface IMLoginCallBack {
        void onError(int code, String desc);

        void onSuccess();
    }
}
