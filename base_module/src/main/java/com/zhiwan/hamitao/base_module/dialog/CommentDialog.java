package com.zhiwan.hamitao.base_module.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiwan.hamitao.base_module.R;
import com.zhiwan.hamitao.base_module.util.KeyBoardUtils;

/**
 * Created by linjianwen on 2018/2/7.
 */

public class CommentDialog extends DialogFragment implements TextWatcher, View.OnClickListener {

    //点击发表，内容不为空时的回调
    public SendListener sendListener;
    private TextView tv_send;
    private String hintText;

    private Dialog dialog;
    private EditText et_content;
    private ImageView iv_emoji;

    View contentView;

    public CommentDialog() {
    }


    @SuppressLint("ValidFragment")
    public CommentDialog(String hintText, SendListener sendBackListener) {//提示文字
        this.hintText = hintText;
        this.sendListener = sendBackListener;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        contentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

//        iv_emoji = (ImageView) contentView.findViewById(R.id.);
        et_content = (EditText) contentView.findViewById(R.id.dialog_comment_content);
        et_content.setHint(hintText);
        tv_send = (TextView) contentView.findViewById(R.id.dialog_comment_send);

        et_content.addTextChangedListener(this);
        tv_send.setOnClickListener(this);
//        iv_emoji.setOnClickListener(this);
        et_content.setFocusable(true);
        et_content.setFocusableInTouchMode(true);
        et_content.requestFocus();

        final Handler handler = new Handler();
       /* dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        CommonUtils.hideSoftKeyBoard(getActivity());
                        ToastUtil.showShort(getActivity(),"显示键盘");
                    }
                }, 200);

            }
        });*/
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KeyBoardUtils.HideKeyboard(et_content);

            }
        });
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
       super.onDismiss(dialog);
       //隐藏软键盘
      /*  InputMethodManager imm = ( InputMethodManager ) et_content.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( et_content.getApplicationWindowToken( ) , 0 );
        }*/




    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        KeyBoardUtils.HideKeyboard(et_content);

    }

    public void cleanText() {
        et_content.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            tv_send.setEnabled(true);
            tv_send.setTextColor(Color.BLACK);
        } else {
            tv_send.setEnabled(false);
            tv_send.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_comment_send) {
            checkContent();
        }
//        else if (i == R.id.iv_emoji) {
//            et_content.setText("");
//
//        }
    }

    private void checkContent() {
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        sendListener.sendComment(content);
        hindKeyboard(et_content);
        KeyBoardUtils.HideKeyboard(et_content);
        dismiss();
    }

    public interface SendListener {
        void sendComment(String inputText);
    }


    public void hindKeyboard(EditText editText) {
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            inputManager.hideSoftInputFromInputMethod(editText.getWindowToken(),0);
        }
    }

}
