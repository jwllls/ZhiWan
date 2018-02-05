package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.AlarmAdapter;
import com.hamitao.zhiwan.model.AlarmModel;
import com.zhiwan.hamitao.base_module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 闹钟
 */
@Route("alarm")
public class AlarmActivity extends BaseActivity implements BGAOnRVItemLongClickListener {

    public static final int ADD_ALARM = 100;

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

        adapter = new AlarmAdapter(recyclerview);

        adapter.setOnRVItemLongClickListener(this);//设置长按监听器

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
                Router.build("add_alarm").requestCode(ADD_ALARM).go(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_ALARM:
                if (resultCode == ADD_ALARM && data != null) {
                    AlarmModel model = (AlarmModel) data.getSerializableExtra("alarmModel");
                    adapter.addItem(0, model);
                }
                break;
        }


    }


    /**
     * 长按点击删除
     *
     * @param parent
     * @param itemView
     * @param position
     * @return
     */
    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {

        //这里进行网络请求，删除闹钟
        adapter.removeItem(position);

        return false;
    }
}
