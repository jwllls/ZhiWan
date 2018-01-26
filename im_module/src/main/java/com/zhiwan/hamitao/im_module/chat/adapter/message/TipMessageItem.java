package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.enums.ChatEnum;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatCoustomDo;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.base_module.util.GsonUtil;
import com.zhiwan.hamitao.im_module.R;


class TipMessageItem extends BaseMessageItem implements OnClickListener {

    private TextView text_toast;

    TipMessageItem(TIMMessage msg, ChatSo chat, BaseActivity context, UserModel friend) {
        super(msg, chat, context, friend);
    }

    @Override
    protected void onInitViews() {

        View view = mInflater.inflate(R.layout.chat_message_tips, null);
        text_toast = (TextView) view.findViewById(R.id.text_toast);

        layout_content.addView(view);
    }

    @Override
    protected void onFillMessage() {
        if (chat == null) return;
        String tips = "";
        int gravity = Gravity.CENTER;
        if (chat.getContentType() == ChatEnum.COUSTOM.getType()) {
            ChatCoustomDo coustomDo = GsonUtil.GsonToBean(chat.getContent(), ChatCoustomDo.class);
            if (coustomDo != null) {
                tips = coustomDo.getText();
//                if (activity instanceof ChatActivity) {
//                    ((ChatActivity) activity).resetServerId(coustomDo.getUserId());
//                }
            }
            gravity = Gravity.CENTER;
        } else if (chat.getContentType() == ChatEnum.TIPS.getType()) {
            if (chat.getContent() != null)
                tips = chat.getContent().toString();
            gravity = Gravity.LEFT;
        }
        text_toast.setGravity(gravity);
        text_toast.setText(tips);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
    }
}
