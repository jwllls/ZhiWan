package com.hamitao.zhiwan.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.NewsModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class SquareAdapter extends BGARecyclerViewAdapter<NewsModel.ResultBean.DataBean> {


    public SquareAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_square_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NewsModel.ResultBean.DataBean model) {
        Glide.with(mContext).load(model.getThumbnail_pic_s02()).into(helper.getImageView(R.id.square_face));
        Glide.with(mContext).load(model.getThumbnail_pic_s()).into(helper.getImageView(R.id.square_img));
        helper.getTextView(R.id.square_title).setText(model.getTitle());
        helper.getTextView(R.id.square_time).setText(model.getDate());



    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
       super.setItemChildListener(helper, viewType);

        helper.setItemChildClickListener(R.id.tv_comments);

        helper.setItemChildClickListener(R.id.tv_good);
    }
}
