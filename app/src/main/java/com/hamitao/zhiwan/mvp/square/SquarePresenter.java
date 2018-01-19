package com.hamitao.zhiwan.mvp.square;

import android.content.Context;

import com.hamitao.zhiwan.base.BasePresenter;
import com.hamitao.zhiwan.model.NewsModel;
import com.hamitao.zhiwan.network.NetWordResult;
import com.hamitao.zhiwan.network.NetWorkCallBack;
import com.hamitao.zhiwan.network.NetworkRequest;
import com.hamitao.zhiwan.util.GsonUtil;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class SquarePresenter implements BasePresenter {

    private SquareView view;

    private Context context;

    public SquarePresenter(SquareView view, Context context) {
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

    /**
     * 获取广场数据
     * @param requestType
     */
    public void getListRequest(final int requestType) {
        NetworkRequest.getSquareList(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
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
