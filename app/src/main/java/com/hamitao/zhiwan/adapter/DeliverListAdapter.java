package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/2/7.
 * 投送清单
 */

public class DeliverListAdapter extends BGARecyclerViewAdapter<String> {


    public DeliverListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_contentlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {

    }
}
