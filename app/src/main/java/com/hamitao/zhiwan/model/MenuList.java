package com.hamitao.zhiwan.model;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class MenuList implements ExpandableListItem {

    public boolean mExpanded = false;
    public String name;
    public List<ContentList> mContentList;


    @Override
    public List<?> getChildItemList() {
        return mContentList;
    }

    @Override
    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void setExpanded(boolean isExpanded) {
        mExpanded = isExpanded;
    }


    @Override
    public String toString(){
        return "MenuList{" +
                "name='" + name + '\'' +
                '}';
    }
}
