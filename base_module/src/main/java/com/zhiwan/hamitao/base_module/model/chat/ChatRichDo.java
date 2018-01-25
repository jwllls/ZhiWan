package com.zhiwan.hamitao.base_module.model.chat;


import com.zhiwan.hamitao.base_module.util.HyperlinkUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zenfer on 2016/3/30.
 */
public class ChatRichDo implements Serializable {

    private String text;
    private List<HyperlinkUtil.HyperlinkModel> linkList;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<HyperlinkUtil.HyperlinkModel> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<HyperlinkUtil.HyperlinkModel> linkList) {
        this.linkList = linkList;
    }
}
