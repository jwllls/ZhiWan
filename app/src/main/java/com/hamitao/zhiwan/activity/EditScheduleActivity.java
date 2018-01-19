package com.hamitao.zhiwan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑课表
 */
public class EditScheduleActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gv_week)
    GridView gvWeek;

    String[] week = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private WeekAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        adapter = new WeekAdapter(this);
        adapter.setData(week);
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("编辑课表");
        gvWeek.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                startActivity(new Intent(this,CreateScheduleActivity.class));
                break;
        }
    }


    class WeekAdapter extends BaseAdapter {

        Context context;


        String[] week;

        public WeekAdapter(Context context) {
            this.context = context;
        }

        public void setData(String[] week) {
            this.week = week;
        }

        @Override
        public int getCount() {
            return week.length;
        }

        @Override
        public Object getItem(int position) {
            return week[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_week_list, parent, false);
                holder = new ViewHolder();
                holder.tv_week = (TextView) convertView.findViewById(R.id.tv_week);
                convertView.setTag(holder);   //将Holder存储到convertView中
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_week.setText(week[position]);
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_week;
    }

}
