package com.zhiwan.hamitao.im_module.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.message.TIMMessageDraft;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.im_module.chat.adapter.ChatRecyclerViewAdapter;
import com.zhiwan.hamitao.im_module.chat.mvp.presenter.ChatPresenter;
import com.zhiwan.hamitao.im_module.chat.mvp.presenter.ChatView;
import com.zhiwan.hamitao.base_module.util.ChatInputMethodUtil;
import com.zhiwan.hamitao.im_module.chat.views.NullMenuEditText;
import com.zhiwan.hamitao.im_module.chat.views.voice_record.CheckAudioPermission;
import com.zhiwan.hamitao.im_module.chat.views.voice_record.MyAudioRecorder;
import com.zhiwan.hamitao.im_module.chat.views.voice_record.RecordButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;



public class ChatActivity extends AppCompatActivity implements ChatView {

    private ChatPresenter presenter;

    /**
     * 对方ID
     */
    private String peer;

    @BindView(R2.id.title_name)
    TextView title_name;
    @BindView(R2.id.more)
    TextView more;

    @BindView(R2.id.recyclerview_list)
    RecyclerView recyclerview_list;

    //底部消息编辑布局
    @BindView(R2.id.layout_bottom)
    FrameLayout layout_bottom;

    //文字编辑
    @BindView(R2.id.layout_text_editor)
    LinearLayout layout_text_editor;
    @BindView(R2.id.edit_text)
    NullMenuEditText edit_text;

    //语音编辑
    @BindView(R2.id.layout_voice_recorder)
    RelativeLayout layout_voice_recorder;
    @BindView(R2.id.btn_record_voice)
    RecordButton btn_record_voice;

    @BindView(R2.id.btn_send_text)
    TextView btn_send_text;

    @BindView(R2.id.view_click)
    View view_click;

    private ChatRecyclerViewAdapter adapter;

    private UserModel user;

    private ChatInputMethodUtil util;

    private int lastItemPosition = 0;

    private boolean hasMessage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);
        ButterKnife.bind(this);
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.SAFE_TO_HOME_DIALOG);
        filter.addAction(Constant.RELOGIN);
        registerReceiver(registerReceiver, filter);
        initView();
        initData();
    }

    private void initView() {
        more.setBackgroundResource(R.drawable.chat_btn_service_n);
        EditUtil.confine(edit_text, Integer.MAX_VALUE, this, "", new EditUtil.EditCallBack() {
            @Override
            public void confine() {
            }

            @Override
            public void length(int len) {
                btn_send_text.setBackgroundResource(len == 0 ? R.drawable.chat_shape_default_black_alpha : R.drawable.chat_btn_send_p);
            }
        });
//        edit_text.requestFocus();
        adapter = new ChatRecyclerViewAdapter(recyclerview_list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        recyclerview_list.setAdapter(adapter);
        //添加间距
        SpacesItemDecoration decoration = new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 10));
        recyclerview_list.addItemDecoration(decoration);
        recyclerview_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isScrolling = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    lastItemPosition = linearManager.findLastCompletelyVisibleItemPosition();
                    if (linearManager.findFirstVisibleItemPosition() == 0 && hasMessage) {
                        presenter.getMessage(adapter.getItem(0), true);
                    }
                }
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        // 判断滚动到底部
                        if (!isFinishing())
                            Glide.with(ChatActivity.this).resumeRequests();
                        isScrolling = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        if (isScrolling) {
                            if (!isFinishing())
                                Glide.with(ChatActivity.this).pauseRequests();
                            isScrolling = false;
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        if (isScrolling) {
                            if (!isFinishing())
                                Glide.with(ChatActivity.this).pauseRequests();
                            isScrolling = false;
                        }
                        break;
                }
            }
        });
        btn_record_voice.setAudioRecord(new MyAudioRecorder());
        btn_record_voice.setRecordListener(new RecordButton.RecordListener() {
            @Override
            public void recordEnd(String filePath, long recordTime) {
                presenter.sendVoiceMessage(user, filePath, recordTime);
            }
        });
    }

    private String safeHelp_str;

    public void initData() {
        CheckAudioPermission.isHasPermission(this);
        //获取配置
        if (ConfigUtil.getInstance().isConfigNull())
            ConfigUtil.getInstance().requestConfig();
        peer = getIntent().getStringExtra("peer");
        if (StringUtil.isBlank(peer)) {
            LogUtil.e("chatActivity —— imId is null");
            return;
        }


        presenter = new ChatPresenter(this, peer + "", TIMConversationType.C2C);
        presenter.start();
        util = new ChatInputMethodUtil(this);

        switch (peer) {
            case ChatConstant.ID_System:
                //系统消息
                user = new UserModel();
                user.setImId(peer);
                user.setUserId(10000);
                user.setNick("系统消息");
                break;
            case ChatConstant.ID_Server:
                //客服消息
                layout_bottom.setVisibility(View.VISIBLE);
                more.setVisibility(View.VISIBLE);
                user = new UserModel();
                user.setImId(peer);
                user.setUserId(PropertiesUtil.getInstance().getLong(ChatConstant.SERVER_ID, 10001));
                user.setNick("客服消息");
                ChatOrderPayDo chatOrderPayDo = (ChatOrderPayDo) getIntent().getSerializableExtra("order");
                if (chatOrderPayDo != null) {
                    // 发送订单消息
                    presenter.sendOrderForServerMessage(user, GsonUtil.GsonToString(chatOrderPayDo));
                }
                safeHelp_str = getIntent().getStringExtra("safeHelp_str");

                break;
            default:
                //用户私聊
                user = UserUtil.user(peer);
                if (user == null) {
                    LogUtil.e("chatActivity —— user is null");
                    return;
                }
                layout_bottom.setVisibility(View.VISIBLE);
                break;
        }
        adapter.setFriend(user);
        title_name.setText(user.getNick());

        if (StringUtil.isNotBlank(safeHelp_str)) {
            presenter.sendTextMessage(user, safeHelp_str);
        }
    }

    /**
     * 重新设置客服ID
     *
     * @param id id
     */
    public void resetServerId(long id) {
        if (id > 0) {
            PropertiesUtil.getInstance().setLong(ChatConstant.SERVER_ID, id);
            user.setUserId(id);
        }
    }

    //-------------------------------------------消息 开始-------------------------------------------//
    private void addMessageToList(final TIMMessage message) {
        //当前聊天界面已读上报，用于多终端登录时未读消息数同步
        if (presenter != null)
            presenter.readMessages();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message != null) {
                    adapter.addLastItem(message);
                    lastItemPosition = adapter.getItemCount() - 1;
                    recyclerview_list.scrollToPosition(lastItemPosition);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 显示消息
     */
    @Override
    public void showMessage(TIMMessage message) {
        addMessageToList(message);
    }

    @Override
    public void showMessage(List<TIMMessage> messages, boolean isLoadMore) {
        Collections.reverse(messages);
        if (isLoadMore) {
            adapter.addNewData(messages);
        } else {
            adapter.setData(messages);
        }
        lastItemPosition = adapter.getItemCount() - 1;
        if (messages.size() > 0)
            recyclerview_list.scrollToPosition(messages.size() - 1);
        else
            hasMessage = false;
    }

    @Override
    public void clearAllMessage() {
    }

    @Override
    public void onSendMessageSuccess(TIMMessage message) {
        sendBroadcast(new Intent(Constant.NEW_MESSAGE));
        sendMessageSucc();
    }

    @Override
    public void onSendMessageFail(int code, String desc, TIMMessage message) {
        showCustomToast(desc);
        sendBroadcast(new Intent(Constant.NEW_MESSAGE));
    }

    @Override
    public void showDraft(TIMMessageDraft draft) {
    }

    //-------------------------------------------消息 结束-------------------------------------------//


    private BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.SAFE_TO_HOME_DIALOG:
                    setSafeToHomePsd();
                    break;
            }
        }
    };

    /**
     * 输入安全到家密码弹窗
     */
    private void setSafeToHomePsd() {
        new SafePsdDialog(ChatActivity.this).setTitle("请输入安全码").builder().setListener(new SafePsdDialog.SafeListener() {
            @Override
            public void complete(String psd) {
                /**
                 * @param code 安全码
                 *             state 状态：0.关闭 1.打开
                 */
                NetWorkRequest.securityCheckCode(psd, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
                    @Override
                    public void onSuccess(NetWordResult result) {
                        showCustomToast("关闭成功");
                    }

                    @Override
                    public void onFail(NetWordResult result, String msg) {
                        new SafeErrorDialog(ChatActivity.this).builder().setTitle(msg).setListener(new SafeErrorDialog.SafeErrorListener() {
                            @Override
                            public void retry() {
                                setSafeToHomePsd();
                            }

                            @Override
                            public void helpByCustomer() {
                                Router.build("chat").with("peer", ChatConstant.ID_Server).with("safeHelp_str",
                                        "我的安全码可能忘记了, 请为我提供帮助").go(ChatActivity.this);
                                finish();

                            }
                        }).show();
                    }

                    @Override
                    public void onBegin() {
                        showProgressDialog();
                    }

                    @Override
                    public void onEnd() {
                        dismissProgressDialog();
                    }
                }));
            }

        }).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null)
            presenter.readMessages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.stop();
        if (registerReceiver != null) unregisterReceiver(registerReceiver);
    }

    @OnTouch({R2.id.view_click})
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            view_click.setVisibility(View.GONE);
            hidenSoftInput();
        }
        return false;
    }

    @OnClick({R2.id.back, R2.id.btn_send_text, R2.id.btn_switch_voice, R2.id.btn_picture_1, R2.id.btn_picture_2
            , R2.id.btn_switch_keyboard, R2.id.edit_text, R2.id.more, R2.id.view_click})
    void onclick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.more) {
            if (!ConfigUtil.getInstance().isConfigNull()) {
                List<ActionSheetDialog.SheetItem> sheetItems = new ArrayList<>();
                ConfigUtil.ConfigModel config = ConfigUtil.getInstance().getConfig();
                if (config.getCouPhones() != null && config.getCouPhones().size() > 0) {
                    for (final String phone : config.getCouPhones()) {
                        ActionSheetDialog.SheetItem sheetItem = new ActionSheetDialog.SheetItem("客服" + phone, ActionSheetDialog.SheetItemColor.Black,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                        sheetItems.add(sheetItem);
                    }
                    new ActionSheetDialog(this).builder()
                            .addAllSheetItem(sheetItems).show();
                }
            }
        } else if (v.getId() == R.id.btn_picture_1 || v.getId() == R.id.btn_picture_2) {
            //选择图片
            new ActionSheetDialog(this).builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.BLUE, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    PictureSelectorUtil.showPictureSelector(ChatActivity.this, 1, 0, 1, onSelectResultCallback);
                }
            }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.BLUE, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    PictureSelectorUtil.showPictureSelector(ChatActivity.this, 2, 0, 1, onSelectResultCallback);
                }
            }).show();
        } else if (v.getId() == R.id.view_click) {
            view_click.setVisibility(View.GONE);
        } else if (v.getId() == R.id.edit_text) {
            util.showSoftInput();//切换输入法的弹出模式
            view_click.setVisibility(View.VISIBLE);
            scrollToLastPosition();
        } else if (v.getId() == R.id.btn_send_text) {
            //发送
            String text = edit_text.getText().toString();
            if (StringUtil.isNotBlank(text)) {
                //发送消息
                presenter.sendTextMessage(user, text);
            }
        } else if (v.getId() == R.id.btn_switch_voice) {
            //切换语音
            if (util != null)
                util.hidenSoftInput(this);
            layout_text_editor.setVisibility(View.GONE);
            layout_voice_recorder.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.btn_switch_keyboard) {
            //切换键盘
            edit_text.requestFocus();
            layout_text_editor.setVisibility(View.VISIBLE);
            layout_voice_recorder.setVisibility(View.GONE);
        }
    }

    private PictureConfig.OnSelectResultCallback onSelectResultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (resultList != null && resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    uploadImg(resultList.get(i).getCompressPath());
                }
            }
        }
    };

    private void uploadImg(String img) {
        Bitmap bitmap = BitmapFactory.decodeFile(img);
        int imgWidth = bitmap.getWidth();
        int imgHeight = bitmap.getHeight();
        presenter.sendImgMessage(user, img, imgWidth, imgHeight);
        bitmap.recycle();
    }

    /**
     * 滚动到最后一项
     */
    private void scrollToLastPosition() {
        if (adapter.getItemCount() > 0)
            layout_text_editor.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerview_list.smoothScrollToPosition(lastItemPosition);
                }
            }, 200);
    }

    /**
     * 消息发送成功
     */
    private void sendMessageSucc() {
        util.hidenSoftInput(this);
        edit_text.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 3:
                if (data == null)
                    return;
                String imgPath = data.getStringExtra("filePath");
                if (StringUtil.isNotBlank(imgPath))
                    uploadImg(imgPath);
                break;
        }
    }


    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }
}