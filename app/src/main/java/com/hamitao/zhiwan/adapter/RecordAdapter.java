package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.RecordFileModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/12.
 */

public class RecordAdapter extends BGARecyclerViewAdapter<RecordFileModel> {



    public RecordAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recordlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, RecordFileModel model) {
        helper.getTextView(R.id.tv_name).setText(model.getRecordFile().getName());
        helper.getTextView(R.id.tv_time).setText(model.getRecordDate());
    }
}
