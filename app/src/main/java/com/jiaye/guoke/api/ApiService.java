package com.jiaye.guoke.api;

import com.jiaye.guoke.bean.AccountBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/16.
 */

public interface ApiService {

    @POST("user/login")
    Observable<AccountBean> loginByPwd(@Body RequestBody body);

    @POST("user/checkMobile")
    Observable<AccountBean> checkMobile(@Body RequestBody body);
}
