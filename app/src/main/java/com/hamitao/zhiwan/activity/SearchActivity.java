package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身",
            "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};

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
    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;

    @BindView(R.id.tv_chage)
    TextView tv_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        etSearch.setEnabled(true);
        editTextable(etSearch, true);
        title.setVisibility(View.VISIBLE);
        title.setText("搜索");
        tvbtn.setText("取消");
        tvbtn.setBackground(null);


        for (int i = 0; i < 10; i++) {


            addLable(tags[i]);
        }


    }


    private void change() {
        int max = tags.length;
        int min = 1;
        Random random = new Random();


        flexboxLayout.removeAllViews();

        for (int i = 0; i < max; i++) {

            int s = random.nextInt(max) % (max - min + 1) + min;
            addLable(tags[s-1]);
        }
    }

    @OnClick({R.id.back, R.id.tv_search, R.id.tv_chage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                //语音识别
                ToastUtil.showShort(this, "语音识别");
                break;
            case R.id.tv_chage:
                change();
                break;

        }
    }


    public void addLable(String label) {
        // 通过代码向FlexboxLayout添加View
        TextView textView = new TextView(this);
        textView.setBackground(getResources().getDrawable(R.drawable.label_bg_shape));
        textView.setText(label);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(30, 20, 30, 20);
        textView.setTextColor(getResources().getColor(R.color.text_default_d));
        flexboxLayout.addView(textView);
        //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
        ViewGroup.LayoutParams params = textView.getLayoutParams();


        if (params instanceof FlexboxLayout.LayoutParams) {
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
            //layoutParams.setFlexBasisPercent(0.3f);

            layoutParams.setMargins(10, 20, 10, 20);

        }

    }

    /**
     * 设置EditText是否可编辑
     *
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
