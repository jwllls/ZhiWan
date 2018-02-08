package com.zhiwan.hamitao.base_module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiwan.hamitao.base_module.R;
import com.zhiwan.hamitao.base_module.util.KeyBoardUtils;

/**
 * Created by linjianwen on 2018/2/7.
 */

public class CommentDialog implements TextWatcher, View.OnClickListener {

    //点击发表，内容不为空时的回调
    public SendListener sendListener;
    private TextView tv_send;
    private String hintText;

    private Dialog dialog;
    private EditText et_content;
    private ImageView iv_emoji;


    private Context context;


    public CommentDialog(Context context, SendListener sendListener) {
        this.context = context;
        this.sendListener = sendListener;
    }


    public CommentDialog builder() {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(context, R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View contentView = View.inflate(context, R.layout.dialog_comment, null);
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

       /* dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HideSoftKeyBoardDialog(context);
            }
        });*/


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                KeyBoardUtils.HideKeyboard(context);
            }
        });

        return this;
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


    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public void show() {
        dialog.show();
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
            Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        sendListener.sendComment(content);
        KeyBoardUtils.HideKeyboard(context);
        dismiss();
    }

    public interface SendListener {
        void sendComment(String inputText);
    }


}
