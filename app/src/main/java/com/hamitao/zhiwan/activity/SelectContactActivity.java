package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.SelectContactAdapter;
import com.hamitao.zhiwan.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SelectContactActivity extends AppCompatActivity {


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

    private SelectContactAdapter adapter;

    private List<ContactModel> list = new ArrayList<>();

    private FinishCallback callback;

    public interface FinishCallback{
        //完成后设置分组名
        void onFinish(String groupName);
    }
    public void setCallback(FinishCallback callback){
        this.callback = callback;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        ButterKnife.bind(this);
        title.setText("选择联系人");
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        initData();
        initView();
    }

    private void initData() {
        adapter = new SelectContactAdapter(recyclerview);

        for (int i = 0; i < 13; i++) {
            ContactModel model = new ContactModel();
            model.setName("我是" + i);
            list.add(model);
        }

        adapter.setData(list);

    }

    private void initView() {
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
//                final EditText et = new EditText(this);
//                new AlertDialog.Builder(this).setMessage("家庭组命名").setView(et)
//                        .setNegativeButton("取消", null)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                ContactModel contactModel = new ContactModel();
////                                contactModel.setName(et.getText().toString());
////                                finishCallback.onFinish();
////                                adapter.addItem(adapter.getData().size(), contactModel);
//
////                                  callback.onFinish(et.getText().toString());
//
//                                    Intent it = new Intent(SelectContactActivity.this,MainActivity.class);
//
//                                    it.putExtra("groupName",et.getText().toString());
//
//                                    setResult(101,it);
//
//                                    finish();
//
//                            }
//                        }).show();

                Router.build("new_group").go(this);


                break;
        }
    }



}
