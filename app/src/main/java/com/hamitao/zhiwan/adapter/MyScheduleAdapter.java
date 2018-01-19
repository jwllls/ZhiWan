package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class MyScheduleAdapter extends BGARecyclerViewAdapter<String> {


    public MyScheduleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_select_contact);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.getTextView(R.id.tv_contact_name).setText(model);
    }
}
