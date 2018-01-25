package com.hamitao.zhiwan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.SquareAdapter;
import com.zhiwan.hamitao.base_module.base.BaseFragment;
import com.hamitao.zhiwan.model.NewsModel;
import com.hamitao.zhiwan.mvp.square.SquarePresenter;
import com.hamitao.zhiwan.mvp.square.SquareView;
import com.zhiwan.hamitao.base_module.util.BGARefreshUtil;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.zhiwan.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class SquareFragment extends BaseFragment implements SquareView, BGARefreshLayout.BGARefreshLayoutDelegate {


    View view;
    Unbinder unbinder;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tv_none;

    private SquareAdapter adapter;
    private SquarePresenter presenter;


    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_square, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }


    private void initData() {

        presenter = new SquarePresenter(this, getActivity());


        refreshLayout.setDelegate(this);//设置下拉刷新监听
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(getActivity())); //设置下拉样式

        adapter = new SquareAdapter(recyclerView);

        startRefreshing();

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    /**
     * 开始刷新
     */
    public void startRefreshing() {
        if (refreshLayout != null) {
            refreshLayout.setVisibility(View.VISIBLE);
            page = 1;
            getList(Constant.REQUEST_REFRESH);
        }
    }

    /**
     * 列表数据请求
     *
     * @param requestType 下拉刷新/加载更多
     */
    private void getList(int requestType) {
        presenter.getListRequest(requestType);
    }

    /**
     * 刷新/加载成功
     */
    public void completeRequest() {
        BGARefreshUtil.completeRequest(refreshLayout);
    }

    /**
     * 设置数据
     *
     * @param models
     * @param requestType
     */
    private void setData(List<NewsModel.ResultBean.DataBean> models, int requestType) {
        if (models == null) {
            return;
        }
        if (requestType == Constant.REQUEST_REFRESH) {
            if (models.size() <= 0) {
                adapter.clear();
            } else {
                adapter.setData(models);
            }
            tv_none.setVisibility(models.size() <= 0 ? View.VISIBLE : View.GONE);
        } else if (requestType == Constant.REQUEST_MORE) {
            adapter.addMoreData(models);
        }
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        getList(Constant.REQUEST_REFRESH);
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        getList(Constant.REQUEST_MORE);
        return true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {
        completeRequest();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(getActivity(), msg);
    }

    @Override
    public void getList(NewsModel.ResultBean resultBean, int requestType) {
        setData(resultBean.getData(), requestType);
    }
}
