package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.RecordAdapter;
import com.hamitao.zhiwan.model.RecordFileModel;
import com.hamitao.zhiwan.util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的录音
 */
public class MyRecordActivity extends AppCompatActivity {

    private RecordAdapter adapter;
    private List<RecordFileModel> list = new ArrayList<>();

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.list_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.list_refresh)
    BGARefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        title.setText("我的录音");
        title.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initData() {
        adapter = new RecordAdapter(recyclerView);
        getRecordList();

    }

    /**
     * 获取本地录音列表
     */
    private void getRecordList() {
        if (list.size() == 0) {
            File file = new File(Constant.USER_RECORD_LOCAL);
            File fa[] = file.listFiles();   //将record文件夹中的文件换为数组

            for (int i = 0; i < fa.length; i++) {
                RecordFileModel model = new RecordFileModel();

                model.setRecordFile(fa[i]);

                model.setRecordDate(DateUtil.formatyyyyMMdd(fa[i].lastModified()));

                list.add(model);
            }
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
            recyclerView.smoothScrollToPosition(0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RECORD_CODE && resultCode == Constant.RECORD_CODE) {
            list = (List<RecordFileModel>) data.getBundleExtra("recordList").getSerializable("recordList");
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
            recyclerView.smoothScrollToPosition(0);//滑动到顶部
        }

    }

    @OnClick({R.id.back, R.id.tv_record, R.id.tv_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_record:
                //进入录音界面
                startActivityForResult(new Intent(this, RecordActivity.class), Constant.RECORD_CODE);
                break;
            case R.id.tv_manager:
                //管理录音文件
                break;
        }
    }
}
