package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.IM.IMHelper;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.enums.ChatEnum;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.base_module.util.UserUtil;
import com.zhiwan.hamitao.im_module.ChatConstant;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.base_module.util.GsonUtil;
import com.zhiwan.hamitao.base_module.util.MusicPlayer;


/**
 * Created by Zenfer on 2016/3/30.
 * 消息体基类
 */
public abstract class BaseMessageItem {

    LayoutInflater mInflater;
    protected TIMMessage msg;
    UserModel friend;
    protected BaseActivity activity;
    protected ChatSo chat;
    private View mRootView;
    private int mBackground;

    BaseMessageItem(TIMMessage message, ChatSo chat, BaseActivity activity, UserModel friend) {
        this.msg = message;
        this.activity = activity;
        this.chat = chat;
        this.friend = friend;
        mInflater = LayoutInflater.from(activity);
    }

    public static BaseMessageItem getInstance(TIMMessage msg, BaseActivity activity, UserModel friend, MusicPlayer mp) {
        if (msg == null)
            return null;
        BaseMessageItem messageItem;
        ChatEnum type = ChatEnum.TEXT;
        if (msg.getElementCount() > 0) {
            TIMElem elem = msg.getElement((int) (msg.getElementCount() - 1));
            switch (elem.getType()) {
                case Custom:
                    TIMCustomElem text = (TIMCustomElem) elem;
                    String data = IMHelper.getMessageContent(text);
                    ChatSo chatSo = GsonUtil.GsonToBean(data, ChatSo.class);
                    if (chatSo == null) {
                        messageItem = new TextMessageItem(msg, null, activity, friend);
                        break;
                    }
                    friend = !msg.isSelf() ? chatSo.getChatUser() : chatSo.getToUser();
                    type = ChatEnum.getType(chatSo.getContentType());
                    if (type == null) {
                        messageItem = new TextMessageItem(msg, null, activity, friend);
                    } else {
                        switch (type) {
                            case TEXT:
                                messageItem = new TextMessageItem(msg, chatSo, activity, friend);
                                break;
                            case IMAGE:
                                messageItem = new ImageMessageItem(msg, chatSo, activity, friend, msg.isSelf());
                                break;
                            case VOICE:
                                messageItem = new VoiceMessageItem(msg, chatSo, activity, friend, mp);
                                break;
                            case RICH_TEXT:// 超文本链接
                                messageItem = new RichMessageItem(msg, chatSo, activity, friend);
                                break;
                            case COUSTOM:
                                //客户消息
                                messageItem = new TipMessageItem(msg, chatSo, activity, friend);
                                break;
                            case TIPS:
                                //提示信息
                                messageItem = new TipMessageItem(msg, chatSo, activity, friend);
                                break;
                            default:
                                messageItem = new TextMessageItem(msg, chatSo, activity, friend);
                                break;
                        }
                    }
                    break;
                default:
                    messageItem = new TextMessageItem(msg, null, activity, friend);
                    break;
            }
        } else {
            messageItem = new TextMessageItem(msg, null, activity, friend);
        }
        messageItem.init(type);
        return messageItem;
    }

    //-------------------------------初始化View 开始------------------------------------//
    private void init(ChatEnum type) {
        if (ChatEnum.COUSTOM == type || ChatEnum.TIPS == type || ChatEnum.ORDER == type
                || ChatEnum.COUPON == type || ChatEnum.RESUND == type) {
            mBackground = R.drawable.chat_bg_message_tips;
            mRootView = mInflater.inflate(R.layout.chat_item_chat_tips, null);
        } else {
            if (!msg.isSelf()) {
                mBackground = R.drawable.chat_bubble_0_r_n;
                mRootView = mInflater.inflate(R.layout.chat_item_chat_receive, null);
            } else {
                mBackground = R.drawable.chat_bubble_0_s_n;
                mRootView = mInflater.inflate(R.layout.chat_item_chat_send, null);
            }
        }
        if (mRootView != null) {
            initViews(mRootView);
        }
    }

    private ImageView image_user_head;
    private ProgressBar image_progress;
    private TextView btn_resend;
    protected LinearLayout layout_content;

    private void initViews(View view) {
        image_user_head = (ImageView) view.findViewById(R.id.image_user_head);
        layout_content = (LinearLayout) view.findViewById(R.id.layout_content);
        image_progress = (ProgressBar) view.findViewById(R.id.image_progress);
        btn_resend = (TextView) view.findViewById(R.id.btn_resend);
        //设置气泡
        layout_content.setBackgroundResource(mBackground);
        //初始化子View
        onInitViews();
    }
    //-------------------------------初始化View 结束------------------------------------//
    //-------------------------------初始化用户资料 开始------------------------------------//

    public void fillContent() {
//        fillStatus();//消息发送状态或已读状态
        fillMessage();
        fillPhotoView(friend);
    }

    private void fillMessage() {
        onFillMessage();
    }

    private void fillPhotoView(UserModel friend) {
        if (!msg.isSelf()) {
            if (msg.getConversation().getPeer().equals(ChatConstant.ID_System)) {
                Glide.with(activity).load(R.drawable.chat_icon_system_message).into(image_user_head);
            } else if (msg.getConversation().getPeer().equals(ChatConstant.ID_Server)) {
                Glide.with(activity).load(R.drawable.chat_icon_server).into(image_user_head);
            } else {
                if (friend == null)
                    return;
                GlideUtil.getInstance().loadCircleImage(activity, image_user_head, friend.getFace()
                        , R.drawable.icon_avarta_circle);
                image_user_head.setTag(R.id.image_tag, friend);
                image_user_head.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        UserModel friend = (UserModel) v.getTag(R.id.image_tag);
                        if (friend != null) {
//                            Router.build("person_info").with("toUserId", friend.getUserId()).go(activity);
                        }
                    }
                });
            }

        } else {
            GlideUtil.getInstance().loadCircleImage(activity, image_user_head, UserUtil.user().getFace()
                    , R.drawable.icon_avarta_circle);
            image_user_head.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO 跳转个人主页
                }
            });
        }
    }

    private void fillStatus() {
        if (msg.isSelf()) {
            switch (chat.getStatus()) {
                case SendSucc: // 发送成功
                    image_progress.setVisibility(View.GONE);
                    btn_resend.setVisibility(View.GONE);
                    break;
                case SendFail: // 发送失败
                    image_progress.setVisibility(View.GONE);
                    btn_resend.setVisibility(View.VISIBLE);
                    break;
                case Sending: // 发送中
                    btn_resend.setVisibility(View.GONE);
                    if (image_progress.getVisibility() != View.VISIBLE)
                        image_progress.setVisibility(View.VISIBLE);
                    break;
                case HasDeleted:
                    image_progress.setVisibility(View.GONE);
                    btn_resend.setVisibility(View.GONE);
                    break;
                default:
                    image_progress.setVisibility(View.GONE);
                    btn_resend.setVisibility(View.GONE);
                    break;
            }
        }
    }
    //-------------------------------初始化用户资料 结束------------------------------------//

    public View getRootView() {
        return mRootView;
    }

    protected abstract void onInitViews();

    protected abstract void onFillMessage();
}
