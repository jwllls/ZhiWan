package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.Switch;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.AlarmModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/2/3.
 */

public class AlarmAdapter extends BGARecyclerViewAdapter<AlarmModel>{


    private Switch mSwitch;

    public AlarmAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_alarm);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, AlarmModel model) {

        helper.getTextView(R.id.tv_time).setText(model.getTime());
        helper.getTextView(R.id.tv_state).setText(model.getLabel()+" ,  "+model.getCycle());
        mSwitch = helper.getView(R.id.switch_isopen);
        mSwitch.setChecked(model.isOpen());

    }


}
