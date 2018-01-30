package com.hamitao.zhiwan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.ContentList;
import com.hamitao.zhiwan.model.ContentListItem;
import com.hamitao.zhiwan.model.MenuList;
import com.hamitao.zhiwan.model.MenuListItem;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 编辑课表
 */
public class EditScheduleActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gv_week)
    GridView gvWeek;

    String[] week = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    private WeekAdapter adapter;

    private final int ITEM_TYPE_MENULIST = 1; //菜单列表项
    private final int ITEM_TYPE_CONTENTLIST = 2;    //内容列表项
    private BaseExpandableAdapter expandableAdapter; //缩放适配器

    private List<MenuList> menulist = new ArrayList<>(); //用于存放列表名


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

        menulist.add(createMenu("我的收藏", false));
        menulist.add(createMenu("官方听单", false));

        expandableAdapter = new BaseExpandableAdapter(menulist) {

            @NonNull
            @Override
            public AbstractAdapterItem<Object> getItemView(Object type) {
                int itemType = (int) type;
                switch (itemType) {
                    case ITEM_TYPE_MENULIST:
                        return new MenuListItem();
                    case ITEM_TYPE_CONTENTLIST:
                        return new ContentListItem();
                }
                return null;
            }

            //必须要这个方法，否则报空
            @Override
            public Object getItemViewType(Object t) {
                if (t instanceof MenuList) {
                    return ITEM_TYPE_MENULIST;
                } else if (t instanceof ContentList)
                    return ITEM_TYPE_CONTENTLIST;
                return -1;
            }


        };


    }


    private MenuList createMenu(String menuName, boolean isExpandDefault) {
        MenuList menu = new MenuList();
        menu.name = menuName;
        List<ContentList> contentLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ContentList content = new ContentList();
            content.content_name = "清单" + i;
            contentLists.add(content);
        }
        menu.mContentList = contentLists;
        menu.mExpanded = isExpandDefault;
        return menu;
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("编辑课表");
        gvWeek.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(expandableAdapter);
    }

    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                startActivity(new Intent(this, CreateScheduleActivity.class));
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
