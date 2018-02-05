package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新密码设置
 */
@Route("new_password")
public class NewPasswordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.rb_showPassword)
    RadioButton rbShowPassword;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("新密码设置");

        rbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShowPassword(isChecked);
            }
        });
    }

    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:

                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    ToastUtil.showShort(this, "修改密码成功");
                    modifyPassword("");
                    finish();
                } else {
                    ToastUtil.showShort(this, "两次输入的密码不相同");
                }

                break;
        }
    }


    /**
     * 是否显示密码
     *
     * @param isShow
     */
    private void isShowPassword(boolean isShow) {
        if (isShow) {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }


    /**
     * 修改密码
     *
     * @param password
     */
    public void modifyPassword(String password) {

        //请求服务器修改密码
    }
}
