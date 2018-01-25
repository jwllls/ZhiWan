package com.zhiwan.hamitao.base_module.IM.listener;

import android.content.Context;
import android.widget.Toast;

import com.langu.frame.utils.LogUtil;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMGroupMemberInfo;

import java.util.List;

/**
 * Created by Zenfer on 2016/10/19.
 */
public class IMGroupAssistantListener implements com.tencent.TIMGroupAssistantListener {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        this.context = context;
    }

    public IMGroupAssistantListener() {
    }

    @Override
    public void onMemberJoin(String s, List<TIMGroupMemberInfo> list) {
        //成员加入群
        for (TIMGroupMemberInfo info : list) {
            LogUtil.d(info.getUser());
            Toast.makeText(context, "群用户加入" + info.getUser(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMemberQuit(String s, List<String> list) {
        //成员退出群
        for (String info : list) {
            LogUtil.d(info);
            Toast.makeText(context, "群用户退出" + info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMemberUpdate(String s, List<TIMGroupMemberInfo> list) {
        for (TIMGroupMemberInfo info : list) {
            LogUtil.d(info.getUser());
            Toast.makeText(context, "群用户更新" + info.getUser(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGroupAdd(TIMGroupCacheInfo timGroupCacheInfo) {
        LogUtil.d(timGroupCacheInfo.getSelfInfo().getJoinTime() + "");
        Toast.makeText(context, "群新增" + timGroupCacheInfo.getSelfInfo().getJoinTime() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGroupDelete(String s) {
        //群解散
        LogUtil.d(s);
        Toast.makeText(context, "群解散" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGroupUpdate(TIMGroupCacheInfo timGroupCacheInfo) {
        LogUtil.d(timGroupCacheInfo.getSelfInfo().getJoinTime() + "");
        Toast.makeText(context, "群更新" + timGroupCacheInfo.getSelfInfo().getJoinTime() + "", Toast.LENGTH_SHORT).show();
    }
}
