package com.hamitao.zhiwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.RecommendAdapter;
import com.hamitao.zhiwan.activity.ScanActivity;
import com.hamitao.zhiwan.activity.SearchActivity;
import com.hamitao.zhiwan.activity.SmartTreeActivity;
import com.hamitao.zhiwan.activity.SortActivity;
import com.hamitao.zhiwan.activity.WebViewActivity;
import com.zhiwan.hamitao.base_module.base.BaseFragment;
import com.hamitao.zhiwan.model.NewsModel;
import com.hamitao.zhiwan.mvp.recommend.RecommendPresenter;
import com.hamitao.zhiwan.mvp.recommend.RecommendView;
import com.zhiwan.hamitao.base_module.util.BGARefreshUtil;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.zhiwan.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class RecommendFragment extends BaseFragment implements RecommendView, BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView vioceSearch; //语音搜索
    @BindView(R.id.tv_btn)
    TextView tvBtn;        //扫一扫
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tv_none;


    private RecommendAdapter adapter;

    private RecommendPresenter presenter;

    private View view;

    private Unbinder unbinder;

    private int page;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new RecommendPresenter(this, getActivity());
        initView();
        return view;
    }

    private void initView() {
        back.setCompoundDrawables(null, null, null, null);
        back.setText("智慧树");
        more.setVisibility(View.VISIBLE);
        more.setText("分类");
        title.setVisibility(View.VISIBLE);
        title.setText("智玩");
        editTextable(etSearch, false);  //不可编辑


        refreshLayout.setDelegate(this);//设置下拉刷新监听
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(getActivity())); //设置下拉样式


        adapter = new RecommendAdapter(recyclerView); // 适配器
        adapter.setOnRVItemClickListener(this);     //设置监听器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //设置列表样式
        recyclerView.setAdapter(adapter); //添加适配器到列表
        startRefreshing();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back, R.id.more, R.id.et_search, R.id.tv_search, R.id.tv_btn, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                startActivity(new Intent(getActivity(), SmartTreeActivity.class));
                break;
            case R.id.more:
                startActivity(new Intent(getActivity(), SortActivity.class));
                break;
            case R.id.et_search:
            case R.id.rl_search:
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_btn:
                //扫一扫
                startActivity(new Intent(getActivity(), ScanActivity.class));
                break;
        }
    }


    /**
     * EditText是否可以编辑
     */
    private void editTextable(EditText editText, boolean editable) {
        if (!editable) { // disable editing password
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            editText.setClickable(false); // user navigates with wheel and selects widget
        } else { // enable editing of password
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setClickable(true);
        }
    }

    /**
     * list 点击事件
     *
     * @param parent
     * @param itemView
     * @param position
     */
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        if (position == 0) {
            //今日推荐
            ToastUtil.showShort(getActivity(), "今日推荐");
        } else if (position == 3) {
            //专家推荐
            ToastUtil.showShort(getActivity(), "专家推荐");
        } else {
            Intent it = new Intent(getActivity(), WebViewActivity.class);
            it.putExtra("result", adapter.getItem(position).getUrl());
            startActivity(it);
        }
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
    public void onBegin() {

    }

    @Override
    public void onFinish() {
        completeRequest();//完成请求
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
