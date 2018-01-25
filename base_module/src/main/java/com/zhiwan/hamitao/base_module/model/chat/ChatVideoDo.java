package com.zhiwan.hamitao.base_module.model.chat;

import java.io.Serializable;

/**
 * Created by Zenfer on 2016/3/30.
 */
public class ChatVideoDo implements Serializable {

    private String url;

    private String imgUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
