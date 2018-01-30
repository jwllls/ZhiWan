package com.hamitao.zhiwan.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class ContentListItem extends AbstractAdapterItem {

    private TextView contentName;
    private CheckBox mCheckBox;
    private ContentList contentList;


    @Override
    public int getLayoutResId() {
        return R.layout.item_contentlist;
    }

    @Override
    public void onBindViews(final View root) {
        contentName = (TextView) root.findViewById(R.id.tv_content);
        mCheckBox = (CheckBox) root.findViewById(R.id.cb_content);
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        if (model instanceof ContentList){
        contentList = (ContentList) model;
        contentName.setText(contentList.content_name);}
    }

}
