package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.RouteCallback;
import com.chenenyu.router.RouteResult;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.AlarmAdapter;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 闹钟
 */
@Route("alarm")
public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;

    private AlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("闹钟");

        more.setVisibility(View.VISIBLE);
        more.setBackgroundResource(R.drawable.add2);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setAdapter(adapter);

    }




    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                Router.build("add_alarm").requestCode(100).callback(new RouteCallback() {
                    @Override
                    public void callback(RouteResult state, Uri uri, String message) {
                        ToastUtil.showShort(AlarmActivity.this,uri.toString()+"========="+message);
                    }
                }).go(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
