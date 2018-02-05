package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.AlarmModel;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.zhiwan.activity.AlarmActivity.ADD_ALARM;

@Route("add_alarm")
public class AddAlarmActivity extends BaseActivity {


    public static final int SELECT_WEEK = 100;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.timepicker)
    TimePicker timepicker;
    @BindView(R.id.tv_bell)
    TextView tvBell;


    private int hour;
    private int minute;

    private String cycle;


    private SimpleArrayMap<Integer, Boolean> map = new SimpleArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        title.setVisibility(View.VISIBLE);
        title.setText("添加闹钟");

        more.setVisibility(View.VISIBLE);
        more.setText("保存");

        timepicker.setIs24HourView(true);

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteofDay) {
                hour = hourOfDay;
                minute = minuteofDay;
            }
        });


        hour = timepicker.getCurrentHour();
        minute = timepicker.getCurrentMinute();
    }


    @OnClick({R.id.back, R.id.more,R.id.rl_alarm_cycle, R.id.rl_alarm_lable, R.id.rl_alarm_bell, R.id.rl_record_bell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                ToastUtil.showShort(this, "保存成功");
                createAlarm();
                break;
            case R.id.rl_alarm_cycle:
                Router.build("select_week").requestCode(SELECT_WEEK).go(this);
                break;
            case R.id.rl_alarm_lable:
                break;
            case R.id.rl_alarm_bell:
                break;
            case R.id.rl_record_bell:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_WEEK && resultCode == SELECT_WEEK) {
            cycle = data.getStringExtra("selectWeek");
//            cycle = String.valueOf(data.getStringArrayExtra("selectWeek"));
            ToastUtil.showShort(this,cycle);

        }
    }

    /**
     * 创建闹钟
     */
    private void createAlarm() {
        AlarmModel model = new AlarmModel();
        model.setLabel("起床了");
        model.setCycle(cycle);
        model.setTime(hour + ":" + minute);
        model.setOpen(true);//保存后默认开启闹钟
        Intent intent = new Intent();
        intent.putExtra("alarmModel", model);
        setResult(ADD_ALARM, intent);
        finish();
    }
}
