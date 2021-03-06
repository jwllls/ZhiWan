package com.hamitao.zhiwan.network;

import com.zhiwan.hamitao.base_module.network.NetWordResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by linjianwen on 2018/1/5.
 *
 *  url 和 请求方法
 */

public interface ServiceApi {


    @FormUrlEncoded
    @POST(Api.AUTOLOGIN)
    Observable<NetWordResult> autoLogin(@FieldMap Map<String, String> map);

    @GET(Api.UPDATE_CHECK)
    Observable<NetWordResult> udapteApk(@QueryMap Map<String, String> map);

    @GET(Api.RECOMMEND_LIST)
    Observable<NetWordResult> getRecommendList();

    @GET(Api.SQUARE_LIST)
    Observable<NetWordResult> getSquareList();


    class Api{
        public static final String BASE_URL = "http://v.juhe.cn/toutiao/";

        //推荐（测试）
        static final String RECOMMEND_LIST = "index?key=f91e041225cb343f3967f4395624f5ed";
        //广场（测试）
        static final String SQUARE_LIST = "index?type=tiyu&key=f91e041225cb343f3967f4395624f5ed";
        //音乐（测试）
        static final String GET_MUSIC = "";

        //自动登录
        static final String AUTOLOGIN = "/api/user/auto/LoginPresenter";

        //退出登陆
        static final String LOGOUT_APP = "/api/user/logout";

        //应用内更新
        static final String UPDATE_CHECK = "/api/app/update/check";

        //用户更新
        static final String UPDATE_USER = "/api/user/update";

        //分享
        static final String SHARE_CONTENT = "/api/app/share";

        /**
         * 资源上传
         */
        static final String FILE_IMAGE_UPLOAD = "/api/aiai/encrypt/image/push";//图片上传
        static final String FILE_VIDEO_UPLOAD = "/api/aiai/encrypt/video/push";//视频上传


    }
}
