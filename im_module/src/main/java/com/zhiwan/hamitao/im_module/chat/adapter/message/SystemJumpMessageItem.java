package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.langu.frame.base.BaseActivity;
import com.langu.frame.model.UserModel;
import com.langu.frame.model.chat.ChatSo;
import com.tencent.TIMMessage;
import com.youyu.chat.R;


/**
 * 系统跳转消息
 */
class SystemJumpMessageItem extends BaseMessageItem implements OnLongClickListener {

    private TextView tv_state, btn_go;
    private ImageView iv_user_head;
    private LinearLayout ll_btn;
    private ChatSo chat;

    SystemJumpMessageItem(TIMMessage msg, ChatSo chat, BaseActivity context, UserModel friend) {
        super(msg, chat, context, friend);
        this.chat = chat;
    }

    @Override
    protected void onInitViews() {
        View view = mInflater.inflate(R.layout.chat_message_order, null);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        btn_go = (TextView) view.findViewById(R.id.btn_go);
        iv_user_head = (ImageView) view.findViewById(R.id.iv_user_head);
        ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
        layout_content.addView(view);
    }

    @Override
    protected void onFillMessage() {
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
