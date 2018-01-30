package com.hamitao.zhiwan.model;

import android.view.View;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class MenuListItem extends AbstractExpandableAdapterItem implements View.OnClickListener{

    private TextView menu_name;
    private MenuList menuList;


    @Override
    public void onExpansionToggled(boolean expanded) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_menu_name;
    }



    @Override
    public void onBindViews(final View root) {

        menu_name = (TextView) root.findViewById(R.id.tv_menu_name);
        menu_name.setOnClickListener(this);
    }

    @Override
    public void onSetViews() {

    }


    @Override
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
       onSetViews();
       menuList = (MenuList) model;
       menu_name.setText(menuList.name);
        ExpandableListItem parentListItem = (ExpandableListItem) model;
        List<?> childItemList = parentListItem.getChildItemList();
      /*  if (childItemList != null && !childItemList.isEmpty()) {
            mArrow.setVisibility(View.VISIBLE);
            mExpand.setText(parentListItem.isExpanded() ? "unexpand" : "expand");
        } else mExpand.setText("expand");*/
    }

    @Override
    public void onClick(View v) {

        if (getExpandableListItem() != null && getExpandableListItem().getChildItemList() != null) {
            if (getExpandableListItem().isExpanded()) {
                collapseView();
            } else {
                expandView();
            }
        }
    }
}
