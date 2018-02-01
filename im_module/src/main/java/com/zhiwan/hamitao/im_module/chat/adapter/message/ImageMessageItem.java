package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatBodyDo;
import com.zhiwan.hamitao.base_module.model.chat.ChatImgDo;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.base_module.customview.ChatImageView;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.base_module.util.GsonUtil;


class ImageMessageItem extends BaseMessageItem implements OnClickListener {

    private BaseActivity activity;
    private boolean isSend;
    private ChatImageView mIvImage;

    ImageMessageItem(TIMMessage msg, ChatSo chat, BaseActivity activity, UserModel friend, boolean isSend) {
        super(msg, chat, activity, friend);
        this.activity = activity;
        this.chat = chat;
        this.isSend = isSend;
        this.friend = friend;
    }

    @Override
    protected void onInitViews() {
        View view = mInflater.inflate(R.layout.chat_message_image, null);
        if (isSend) {
            mIvImage = (ChatImageView) view.findViewById(R.id.message_image_right);
        } else {
            mIvImage = (ChatImageView) view.findViewById(R.id.message_image_left);
        }
        layout_content.addView(view);
        layout_content.setBackgroundColor(ContextCompat.getColor(activity, R.color.transparent));
    }


    @Override
    protected void onFillMessage() {
        if (chat != null) {
            ChatBodyDo bodyDo = GsonUtil.GsonToBean(chat.getContent(), ChatBodyDo.class);
            if (bodyDo == null)
                return;
            ChatImgDo imgDo = GsonUtil.GsonToBean(bodyDo.getContent(), ChatImgDo.class);
            if (imgDo != null) {
                int imgHeight;
                int imgWidth;
                if (imgDo.getImgWidth() == 0 || imgDo.getImgHeight() == 0) {
                    imgHeight = imgWidth = (int) (BaseActivity.mScreenHeight * 0.3);
                } else {
                    if (imgDo.getImgWidth() <= imgDo.getImgHeight()) {
                        imgHeight = (int) (BaseActivity.mScreenHeight * 0.25);
                        imgWidth = imgHeight * imgDo.getImgWidth() / imgDo.getImgHeight();
                    } else {
                        imgWidth = (int) (BaseActivity.mScreenWidth * 0.4);
                        imgHeight = imgWidth * imgDo.getImgHeight() / imgDo.getImgWidth();
                    }
                }
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgWidth, imgHeight);
                mIvImage.setLayoutParams(params);
                mIvImage.setTag(R.id.image_tag, imgDo.getImgUrl());
                mIvImage.setOnClickListener(this);
//                GlideUtil.getInstance().loadImage(activity, mIvImage, imgDo.getImgUrl(), false);
                Glide.with(activity).load(imgDo.getImgUrl()).into(mIvImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
      /*  String imgUrl = (String) v.getTag(R.id.image_tag);
        if (StringUtil.isNotBlank(imgUrl)) {
            List<PhotoModel> photoModels = new ArrayList();
            PhotoModel model = new PhotoModel();
            model.setUrl(imgUrl);
            photoModels.add(model);
            Router.build("photo_pre").with("photo_list", GsonUtil.GsonToString(photoModels)).requestCode(0).go(activity);
        }*/
    }

}
