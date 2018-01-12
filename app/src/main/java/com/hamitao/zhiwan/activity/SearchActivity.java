package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_btn)
    TextView tvbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        etSearch.setEnabled(true);
        editTextable(etSearch,true);
        tvbtn.setText("取消");
        tvbtn.setBackground(null);

    }

    @OnClick({R.id.back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                break;
        }
    }

    /**
     * 设置EditText是否可编辑
     * @param editText
     * @param editable
     */
    private void editTextable(EditText editText, boolean editable) {
        if (!editable) { // disable editing password
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            editText.setClickable(false); // user navigates with wheel and selects widget
        } else { // enable editing of password
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setClickable(true);
        }
    }

}
