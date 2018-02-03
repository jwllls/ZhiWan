package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的设备
 */
public class MachineActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_machine_state)
    RelativeLayout rlMachineState;
    @BindView(R.id.rl_machine_electric)
    RelativeLayout rlMachineElectric;
    @BindView(R.id.rl_machine_signal)
    RelativeLayout rlMachineSignal;
    @BindView(R.id.rl_machine_wifi)
    RelativeLayout rlMachineWifi;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_delivert_list)
    TextView tvDelivertList;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_touch)
    TextView tvTouch;
    @BindView(R.id.rl_machine_isOpen)
    RelativeLayout rlMachineIsOpen;
    @BindView(R.id.rl_machine_play)
    RelativeLayout rlMachinePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("设备管理");

    }

    @OnClick({R.id.back, R.id.more,R.id.rl_machine_state, R.id.rl_machine_electric, R.id.rl_machine_signal, R.id.rl_machine_wifi, R.id.tv_alarm, R.id.tv_delivert_list, R.id.tv_contact, R.id.tv_phone, R.id.tv_touch, R.id.rl_machine_isOpen, R.id.rl_machine_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.rl_machine_state:
                break;
            case R.id.rl_machine_electric:
                break;
            case R.id.rl_machine_signal:
                break;
            case R.id.rl_machine_wifi:
                break;
            case R.id.tv_alarm:
                Router.build("alarm").go(this);
                break;
            case R.id.tv_delivert_list:
                break;
            case R.id.tv_contact:
                break;
            case R.id.tv_phone:
                break;
            case R.id.tv_touch:
                break;
            case R.id.rl_machine_isOpen:
                break;
            case R.id.rl_machine_play:
                break;
        }
    }
}
