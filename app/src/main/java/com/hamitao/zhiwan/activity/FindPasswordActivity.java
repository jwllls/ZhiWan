package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.util.StringUtil;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("find_password")
public class FindPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.title)
    TextView title;

    //随机生成的六位验证码
    private String code = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        title.setVisibility(View.VISIBLE);

        title.setText("找回密码");
    }

    @OnClick({R.id.back, R.id.tv_getCode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_getCode:


                code = createCode();

                ToastUtil.showLong(this, "智玩验证码：" + code);





                break;
            case R.id.btn_next:
                if (etUsername != null && !etUsername.equals("")) {

                    if (StringUtil.isMobileNO(etUsername.getText().toString())) {  //判断正确手机格式

                        if (code.equals(etCode.getText().toString().trim())) {

                            code = createCode(); //打乱验证码

                            Router.build("new_password").go(this);

                        } else {

                            ToastUtil.showShort(this, "验证码错误");
                        }
                    } else {
                            ToastUtil.showShort(this, "请输入正确的手机号");
                    }
                }
                break;
        }
    }

    /**
     * 生成6位随机验证码
     *
     * @return
     */
    private String createCode() {
        Random rad = new Random();
        return rad.nextInt(1000000) + "";
    }
}
