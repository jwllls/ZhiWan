package com.hamitao.zhiwan.network;


import com.hamitao.zhiwan.util.LogUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linjianwen on 2018/1/5.
 *
 * 网络代理类
 */

public class NetWork {


    private static final int DEFAULT_TIMEOUT = 10;//超时时长

    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();

    private static final RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    private static ServiceApi serviceApi;

    public static ServiceApi getApi() {
        if (serviceApi == null) {
            serviceApi = getRetrofit(ServiceApi.Api.BASE_URL).create(ServiceApi.class);
        }
        return serviceApi;
    }

    private static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(new LogInterceptor()).build())
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtil.i("request:", request.toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtil.i("response body:", content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }
}
