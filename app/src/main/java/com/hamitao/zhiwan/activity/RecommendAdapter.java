package com.hamitao.zhiwan.activity;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.NewsModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class RecommendAdapter extends BGARecyclerViewAdapter<NewsModel.ResultBean.DataBean> {

    public RecommendAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NewsModel.ResultBean.DataBean model) {


        if (position == 0 || position == 3) {
            helper.getTextView(R.id.tv_type).setText(position == 0 ? "今日推荐" : "专家推荐");
        } else {
            Glide.with(mContext).load(model.getThumbnail_pic_s()).into(helper.getImageView(R.id.iv_img));
            helper.getTextView(R.id.tv_title).setText(model.getTitle());
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 3) {
            return R.layout.item_recommend_tips;
        } else {
            return R.layout.item_recommend_list;
        }
    }


}
