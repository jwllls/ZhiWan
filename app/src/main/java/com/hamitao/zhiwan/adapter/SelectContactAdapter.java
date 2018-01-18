package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.ContactModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/18.
 * 选择联系人
 */

public class SelectContactAdapter extends BGARecyclerViewAdapter<ContactModel>{


    public SelectContactAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_select_contact);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ContactModel model) {
        helper.getTextView(R.id.tv_contact_name).setText(model.getName());
    }
}
