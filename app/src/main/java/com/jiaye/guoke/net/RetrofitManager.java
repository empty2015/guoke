package com.jiaye.guoke.net;

import com.jiaye.guoke.util.GsonManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/12.
 */

public class RetrofitManager {
    private static volatile RetrofitManager manager;
    private OkHttpClient okHttpClient;
    private Retrofit mRetrofit;
    private RetrofitManager(){
        init();
    }
    public static RetrofitManager getInstance(){
        if(manager==null){
            synchronized (RetrofitManager.class){
                if(manager==null){
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    private void init(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ApiConfig.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(ApiConfig.DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonManager.getInstance().getGson()))
                .baseUrl(getServiceUrl())
                .build();
    }

    public String getServiceUrl(){
        return ApiConfig.API_SERVICE_URL_TEST;
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
