package com.hamitao.zhiwan.mvp.square;

import com.hamitao.zhiwan.base.BaseView;
import com.hamitao.zhiwan.model.NewsModel;

/**
 * Created by linjianwen on 2018/1/19.
 */

public interface SquareView extends BaseView {

    void getList(NewsModel.ResultBean resultBean, int requestType);
}