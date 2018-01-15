package com.hamitao.zhiwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.ScanActivity;
import com.hamitao.zhiwan.activity.SearchActivity;
import com.hamitao.zhiwan.activity.SmartTreeActivity;
import com.hamitao.zhiwan.activity.SortActivity;
import com.hamitao.zhiwan.base.BaseFragment;
import com.hamitao.zhiwan.model.NewsModel;
import com.hamitao.zhiwan.network.NetWork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class RecommendFragment extends BaseFragment {



    private View view;
    Unbinder unbinder;

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

    NewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        more.setVisibility(View.VISIBLE);
        more.setText("分类");
        title.setVisibility(View.VISIBLE);
        title.setText("智玩");
        editTextable(etSearch,false);  //不可编辑

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        NetWork.getApi().getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NewsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NewsModel> newsModelResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("news","出错");
                    }

                    @Override
                    public void onComplete() {
                    }
                });





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back, R.id.more,R.id.et_search, R.id.tv_search, R.id.tv_btn,R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                startActivity(new Intent(getActivity(), SmartTreeActivity.class));
                break;
            case R.id.more:
                startActivity(new Intent(getActivity(), SortActivity.class));
                break;
            case R.id.et_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.rl_search:
            case R.id.tv_search:
                //语音输入后直接跳转到搜索页面 SearchActivity
                break;
            case R.id.tv_btn:
                //扫一扫
                startActivity(new Intent(getActivity(), ScanActivity.class));
                break;
        }
    }

    /**
     * EditText 是否可以编辑
     * @param editText
     * @param editable
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


    private class NewAdapter  extends BGARecyclerViewAdapter<NewsModel> {


        public NewAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_news);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, NewsModel model) {
            helper.getTextView(R.id.news_title).setText(model.getResult().getData().get(position).getTitle());
        }
    }


}
