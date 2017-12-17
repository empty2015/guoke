package com.jiaye.guoke.net;

import com.jiaye.guoke.api.ApiService;
import com.jiaye.guoke.bean.AccountBean;
import com.jiaye.guoke.bean.LoginRequestBean;
import com.jiaye.guoke.net.interfaces.IHttpResult;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/9.
 */

public class ApiManager {
    private static volatile ApiManager apiManager = null;
    ApiService apiService;
    private ApiManager(){
        apiService =  RetrofitManager.getInstance().create(ApiService.class);
    }

    public static ApiManager getInstance(){
        if(apiManager ==null){
            synchronized (ApiManager.class){
                if(apiManager ==null){
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 根据帐号密码登录
     * @param mobile
     * @param pwd
     * @param httpResult
     */
    public void login(String mobile, String pwd, IHttpResult<AccountBean> httpResult){
        LoginRequestBean bean = new LoginRequestBean();
        bean.setMobile(mobile);
        bean.setPassword(pwd);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),convert(bean));
        Observable<AccountBean> observable = apiService.loginByPwd(requestBody);
        RxUtil.request("loginByPwd",observable,httpResult);
    }

    private String convert(Object bean){
        return RxUtil.beanConvertToStr(bean);
    }
}
