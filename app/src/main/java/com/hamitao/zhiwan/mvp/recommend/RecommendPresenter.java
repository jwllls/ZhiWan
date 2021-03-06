package com.hamitao.zhiwan.mvp.recommend;

import android.content.Context;

import com.zhiwan.hamitao.base_module.base.BasePresenter;
import com.hamitao.zhiwan.model.NewsModel;
import com.zhiwan.hamitao.base_module.network.NetWordResult;
import com.zhiwan.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.zhiwan.network.NetworkRequest;
import com.zhiwan.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class RecommendPresenter implements BasePresenter {

    private RecommendView view;

    private Context context;

    public RecommendPresenter(RecommendView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
    }


    //获取列表数据
    public void getListRequest(final int requestType) {
        NetworkRequest.getRecommendList(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWordResult result) {
                NewsModel.ResultBean resultBean = GsonUtil.GsonToBean(result.getResult(), NewsModel.ResultBean.class);
                view.getList(resultBean,requestType);
            }

            @Override
            public void onFail(NetWordResult result, String msg) {
               view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));


    }
}
