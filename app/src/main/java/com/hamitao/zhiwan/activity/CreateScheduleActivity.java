package com.hamitao.zhiwan.activity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.ArrayTableData;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.DensityUtils;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateScheduleActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.table)
    SmartTable<Integer> table;

    String[] week = {"日"+"\n16:50","一","二","三","四","五","六"};

    Integer[][] infos = {{0,1,2,1,1,0,1,1,0,1,1,2,3}, {4,2,1,1,0,1,1,0,1,1,2,2,3},
            {2,2,0,1,2,4,1,0,1,3,0,1,1},{2,1,1,0,1,4,0,1,1,2,2,0,3},
            {0,1,2,4,1,0,1,4,0,1,1,2,2}, {1,0,1,3,2,2,0,1,2,1,1,0,4},
            {3,1,2,4,0,1,2,1,1,0,1,1,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        ButterKnife.bind(this);

        initData();
        initView();
        FontStyle fontStyle = new FontStyle(this,10, ContextCompat.getColor(this,R.color.text_default_l));//字体样式
        table.getConfig().setColumnTitleStyle(fontStyle); //标题样式
        table.getConfig().setShowTableTitle(false);//隐藏标题
        table.getConfig().setFixedFirstColumn(true);
//        table.getConfig().setFixedCountRow(true);
        table.getConfig().setHorizontalPadding(0);
        table.getConfig().setVerticalPadding(0);
        table.getConfig().setGridStyle(new LineStyle());//表格样式


        //创建数据表
        ArrayTableData<Integer> tableData = ArrayTableData.create("课程表",week,infos,new IDrawFormat<Integer>(){

            @Override
            public int measureWidth(Column<Integer> column, TableConfig config) {
                return DensityUtils.dp2px(CreateScheduleActivity.this,80);
            }

            @Override
            public int measureHeight(Column<Integer> column, int position, TableConfig config) {
                return DensityUtils.dp2px(CreateScheduleActivity.this,80);
            }

            @Override
            public void draw(Canvas c, Column<Integer> column, Integer integer, String value, Rect rect, int position, TableConfig config) {
                Paint paint = config.getPaint();
                int color;
                switch (integer){
                    case 1:
                        color =R.color.text_default_d;
                        break;
                    case 2:
                        color =R.color.text_default_d_p;
                        break;
                    case 3:
                        color =R.color.text_default_l;
                        break;
                    case 4:
                        color =R.color.text_default_l_p;
                        break;
                    default:
                        color =R.color.divider;
                        break;
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(CreateScheduleActivity.this, color));
                c.drawRect(rect.left+5,rect.top+5,rect.right-5,rect.bottom-5,paint);
            }
        });


        tableData.setOnItemClickListener(new ArrayTableData.OnItemClickListener<Integer>() {
            @Override
            public void onClick(Column column, String value, Integer o, int col, int row) {
                ToastUtil.showShort(CreateScheduleActivity.this,"列:"+col+ " 行："+row + "数据："+value);
            }
        });


        table.setTableData(tableData);
    }

    private void initData() {

    }

    private void initView() {

    }


    @OnClick({R.id.back, R.id.more, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.btn_create:
                break;
        }
    }
}
