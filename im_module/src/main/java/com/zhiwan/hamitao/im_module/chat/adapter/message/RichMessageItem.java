package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatRichDo;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.base_module.util.HyperlinkUtil;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.base_module.util.GsonUtil;


class RichMessageItem extends BaseMessageItem implements OnLongClickListener {

    private TextView mEtvContent;
    protected ChatSo chat;

    RichMessageItem(TIMMessage msg, ChatSo chat, BaseActivity activity, UserModel friend) {
        super(msg, chat, activity, friend);
        this.chat = chat;
    }

    @Override
    protected void onInitViews() {
        View view = mInflater.inflate(R.layout.chat_message_rich, null);
        mEtvContent = (TextView) view.findViewById(R.id.tv_rich);
        mEtvContent.setMovementMethod(LinkMovementMethod.getInstance());// textview只有加上这句代码才能实现多个点击事件的响应
        layout_content.addView(view);
    }

    @Override
    protected void onFillMessage() {
        if (!msg.isSelf()) {
            mEtvContent.setTextColor(ContextCompat.getColor(activity, R.color.text_default_d));
        } else {
            mEtvContent.setTextColor(ContextCompat.getColor(activity, R.color.white));
        }
        if (chat == null || chat.getContent() == null) {
            return;
        }
        ChatRichDo richDo = GsonUtil.GsonToBean(chat.getContent(), ChatRichDo.class);
        if (richDo != null) {
            mEtvContent.setText(HyperlinkUtil.getRichText(activity, richDo.getText(), richDo.getLinkList()));
        }
    }

    @Override
    public boolean onLongClick(View v) {

        return true;
    }

}
