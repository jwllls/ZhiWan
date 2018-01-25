package com.zhiwan.hamitao.im_module.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.im_module.chat.adapter.message.BaseMessageItem;
import com.zhiwan.hamitao.base_module.util.DateUtil;
import com.zhiwan.hamitao.base_module.util.MusicPlayer;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * 作者:Zenfer
 */
public class ChatRecyclerViewAdapter extends BGARecyclerViewAdapter<TIMMessage> {

    private static final long MIN_RECENT_TIME = 60 * 1000;

    private Context activity;
    private UserModel friend;

    private MusicPlayer mp = null;

    public void setFriend(UserModel friend){
        this.friend = friend;
    }

    public ChatRecyclerViewAdapter(RecyclerView recyclerView, Context activity){
        super(recyclerView, R.layout.chat_item_chat);
        this.activity = activity;
        mp = new MusicPlayer(activity, null);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, TIMMessage message){
        //是否显示时间
        fillTimeStamp(bgaViewHolderHelper.getTextView(R.id.message_time), i, message.timestamp() * 1000);
        BaseMessageItem messageItem = BaseMessageItem.getInstance(message, activity, friend, mp);
        messageItem.fillContent();
        LinearLayout layout_message = bgaViewHolderHelper.getView(R.id.layout_message);
        layout_message.removeAllViews();
        layout_message.addView(messageItem.getRootView());
    }

    /**
     * 显示消息时间
     *
     * @param text
     * @param position
     * @param time
     */
    public void fillTimeStamp(TextView text, int position, long time){
        if(position > 0 && (time - getData().get(position - 1).timestamp() * 1000) < MIN_RECENT_TIME){
            //消息间隔时间<1分钟
            text.setVisibility(View.GONE);
        }else{
            //消息间隔时间>=1分钟
            text.setVisibility(View.VISIBLE);
            if(time != 0){
                if(time > DateUtil.getDayBegin(System.currentTimeMillis())){
                    // 当天时间
                    text.setText(DateUtil.formatHHmm(time));
                }else if(time > DateUtil.getDayBegin(DateUtil.getDayBegin(System.currentTimeMillis()) - 3000 * 1000)){
                    // 昨天时间
                    text.setText("昨天 " + DateUtil.formatHHmm(time));
                }else if(time > DateUtil.getDayBegin(DateUtil.getDayBegin(System.currentTimeMillis()) - 7 * 24 * 60 * 60 * 1000)){
                    // 一周内时间
                    text.setText(DateUtil.formatEHHmm(time));
                }else{
                    text.setText(DateUtil.formatMMddHHmm(time));
                }
            }
        }
    }
}