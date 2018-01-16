package com.hamitao.zhiwan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.RecordAdapter;
import com.hamitao.zhiwan.model.RecordModel;
import com.hamitao.zhiwan.util.DateUtil;
import com.hamitao.zhiwan.util.FileUtil;
import com.hamitao.zhiwan.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的录音
 */
public class MyRecordActivity extends AppCompatActivity implements BGAOnItemChildClickListener, BGAOnRVItemClickListener {

    private RecordAdapter adapter;
    private List<RecordModel> list = new ArrayList<>();

    File file = new File(Constant.USER_RECORD_LOCAL);
    File fa[] = file.listFiles();   //将record文件夹中的文件换为数组

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tv_none;
    @BindView(R.id.tv_count)
    TextView tv_count; //录音文件数


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
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
        tv_count.setText("我的录音(" + (fa.length) + ")");
        getRecordList();
    }

    /**
     * 获取本地录音列表
     */
    private void getRecordList() {
        if (list.size() == 0) {

            if (fa.length > 0) {
                for (int i = fa.length - 1; i >= 0; i--) {
                    RecordModel model = new RecordModel();

                    model.setRecordFile(fa[i]);

                    model.setRecordDate(DateUtil.formatyyyyMMdd(fa[i].lastModified()));

                    try {
                        model.setFileSize(FileUtil.formatFileSize(FileUtil.getFileSize(fa[i])));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    list.add(model);

                    tv_none.setVisibility(View.GONE);
                }

            } else {
                tv_none.setVisibility(View.VISIBLE);
            }

            adapter.setData(list);
            recyclerView.setAdapter(adapter);
            recyclerView.smoothScrollToPosition(0); //回到的第一条录音
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RECORD_CODE && resultCode == Constant.RECORD_CODE) {
            list = (List<RecordModel>) data.getBundleExtra("recordList").getSerializable("recordList");
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


    /**
     * 点击子View
     *
     * @param parent
     * @param childView
     * @param position
     */
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, final int position) {

        if (childView.getId() == R.id.tv_more) {

            new AlertDialog.Builder(this).setTitle("更多")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showShort(MyRecordActivity.this, "更多："+position);
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }


    }

    /**
     * 点击父View
     *
     * @param parent
     * @param itemView
     * @param position
     */
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtil.showShort(this, "播放：" + position);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            Uri uri = Uri.fromFile(list.get(position).getRecordFile());
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
