package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.base_module.util.StringUtil;


class TextMessageItem extends BaseMessageItem implements OnLongClickListener {

    private TextView mEtvContent;
    private ChatSo chat;

    TextMessageItem(TIMMessage msg, ChatSo chat, BaseActivity context, UserModel friend) {
        super(msg, chat, context, friend);
        this.chat = chat;
    }

    @Override
    protected void onInitViews() {
        View view = mInflater.inflate(R.layout.chat_message_text, null);
        mEtvContent = (TextView) view.findViewById(R.id.tv_msgtext);
        layout_content.addView(view);
    }

    @Override
    protected void onFillMessage() {
        if (!msg.isSelf()) {
            mEtvContent.setTextColor(ContextCompat.getColor(activity, R.color.text_default_d));
        } else {
            mEtvContent.setTextColor(ContextCompat.getColor(activity, R.color.white));
        }
        if (chat == null) {
            mEtvContent.setText("[请更新到最新版本查看]");
            return;
        }
        if (null != chat.getContent()) {
            mEtvContent.setText(StringUtil.contentFilter(chat.getContent().toString()));
        }
    }

    @Override
    public boolean onLongClick(View v) {

        return true;
    }
}
