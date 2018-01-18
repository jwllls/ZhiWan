package com.hamitao.zhiwan.adapter;


import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/18.
 */

public class ChatGroupAdapter extends BGARecyclerViewAdapter<String> {


    public ChatGroupAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chat_group);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String groupName) {
        helper.getTextView(R.id.tv_group_name).setText(groupName);
    }
}
