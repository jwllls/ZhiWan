package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.zhiwan.activity.AddAlarmActivity.SELECT_WEEK;

@Route("select_week")
public class SelectWeekActivity extends AppCompatActivity {

    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;
    @BindView(R.id.cb7)
    CheckBox cb7;

    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                selectWeek();

                Intent it = new Intent();

                it.putExtra("selectWeek", stringBuilder.toString());

                setResult(SELECT_WEEK, it);

                finish();
                break;
            case R.id.more:
                break;
        }
    }

    private void selectWeek() {

        if (cb1.isChecked()) {
            stringBuilder.append("周一 ");
        }

        if (cb2.isChecked()) {
            stringBuilder.append("周二 ");
        }

        if (cb3.isChecked()) {
            stringBuilder.append("周三 ");
        }
        if (cb4.isChecked()) {
            stringBuilder.append("周四 ");
        }

        if (cb5.isChecked()) {
            stringBuilder.append("周五 ");
        }

        if (cb6.isChecked()) {
            stringBuilder.append("周六 ");
        }
        if (cb7.isChecked()) {
            stringBuilder.append("周日 ");
        }

    }


}
