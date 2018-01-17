package com.hamitao.zhiwan.mvp.recommend;

import com.hamitao.zhiwan.base.BaseView;
import com.hamitao.zhiwan.model.NewsModel;

/**
 * Created by linjianwen on 2018/1/17.
 */

public interface RecommendView extends BaseView {

    /**
     * 返回列表数据
     */
    void getList(NewsModel.ResultBean resultBean,int requestType);

}
