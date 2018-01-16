package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.RecordModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * Created by linjianwen on 2018/1/12.
 */

public class RecordAdapter extends BGARecyclerViewAdapter<RecordModel> {



    public RecordAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recordlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, RecordModel model) {
        helper.getTextView(R.id.tv_name).setText((position+1)+". "+model.getRecordFile().getName());
        helper.getTextView(R.id.tv_time).setText(model.getRecordDate());
        helper.getTextView(R.id.tv_size).setText(model.getFileSize());

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.tv_more);
    }
}
