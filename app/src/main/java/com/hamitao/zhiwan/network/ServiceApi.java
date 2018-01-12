package com.hamitao.zhiwan.network;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by linjianwen on 2018/1/5.
 */

public interface ServiceApi {


    @FormUrlEncoded
    @POST(Api.AUTOLOGIN)
    Observable<NetWordResult> autoLogin(@FieldMap Map<String, String> map);

    @GET(Api.UPDATE_CHECK)
    Observable<NetWordResult> udapteApk(@QueryMap Map<String, String> map);


    class Api{
        public static final String BASE_URL = "";

        //自动登录
        static final String AUTOLOGIN = "/api/user/auto/login";

        //退出登陆
        static final String LOGOUT_APP = "/api/user/logout";

        //应用内更新
        static final String UPDATE_CHECK = "/api/app/update/check";

        //用户更新
        static final String UPDATE_USER = "/api/user/update";

        //推荐列表
        static final String RECOMMEND_LIST = "/api/invite/recommend/list";

        //分享
        static final String SHARE_CONTENT = "/api/app/share";

        /**
         * 资源上传
         */
        static final String FILE_IMAGE_UPLOAD = "/api/aiai/encrypt/image/push";//图片上传
        static final String FILE_VIDEO_UPLOAD = "/api/aiai/encrypt/video/push";//视频上传


    }
}
