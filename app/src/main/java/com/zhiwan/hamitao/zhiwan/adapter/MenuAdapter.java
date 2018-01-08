package com.zhiwan.hamitao.zhiwan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiwan.hamitao.zhiwan.R;
import com.zhiwan.hamitao.zhiwan.model.MenuModel;
import com.zhiwan.hamitao.zhiwan.util.ScreenUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linjianwen on 2018/1/8.
 */

public class MenuAdapter extends BaseAdapter{


    private List<MenuModel> models;

    private Context context;

    private int itemWidth;

    public MenuAdapter(List<MenuModel> models, Context context) {
        this.models = models;
        this.context = context;
        this.itemWidth = ScreenUtil.getScreenWidth(context)/4;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            //这里面的item是一个自定义的View继承线性布局，继承什么布局不重要，
            // 重要的是将item的宽高设置成一样；感觉这个效果项目中很多地方都能用到
            convertView= View.inflate(context, R.layout.item_menulist, null);
            vh=new ViewHolder();
            vh.ll_item=(LinearLayout) convertView.findViewById(R.id.ll_item);
            vh.iv_bg = convertView.findViewById(R.id.iv_bg);
            vh.tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);

            ViewGroup.LayoutParams lp=vh.ll_item.getLayoutParams();
            lp.width=itemWidth;
            vh.ll_item.setLayoutParams(lp);

            convertView.setTag(vh);
        }else{
            vh=(ViewHolder) convertView.getTag();

        }
        //当前item要加载的图片路径

        MenuModel model = models.get(position);
        vh.iv_bg.setBackground(model.getDrawable());
        vh.tv_menu.setText(model.getMenuName());

        return convertView;
    }

    private class ViewHolder{

        LinearLayout ll_item;

        CircleImageView iv_bg;

        TextView tv_menu;

    }


//    public MenuAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
//        super(recyclerView, defaultItemLayoutId);
//    }
//
//    @Override
//    protected void fillData(BGAViewHolderHelper helper, int position, MenuModel model) {
//        (helper.getView(R.id.civ)).setBackground(model.getDrawable());
//        helper.getTextView(R.id.tv_menu).setText(model.getMenuName());
//    }

}
