package com.jiaye.guoke.module.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jiaye.guoke.MyApplication;
import com.jiaye.guoke.bean.UserBean;
import com.jiaye.guoke.net.RxUtil;

/**
 * Created by Administrator on 2017/12/16.
 */

public class LoginManager {
    private static final String SP_ACCOUNT_FILE = "SP_ACCOUNT_FILE";
    private static final String SP_ACCOUNT_USER = "SP_ACCOUNT_USER";
    private static volatile LoginManager instance;
    private UserBean userInfo = null;
    private LoginManager() {

    }
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void setUserInfo(UserBean userInfo){
        this.userInfo = userInfo;
        setCacheUserInfo(userInfo);
    }

    public UserBean getUserInfo(){
        if (userInfo == null){
            userInfo = getUserInfoFromCache();
        }
        return userInfo;
    }

    private UserBean getUserInfoFromCache(){
        SharedPreferences sharedPreferences = MyApplication.getApplication().getSharedPreferences(SP_ACCOUNT_FILE, Context.MODE_PRIVATE);
        String jsonStr =  sharedPreferences.getString(SP_ACCOUNT_USER,"");
        if (!TextUtils.isEmpty(jsonStr)) {
            return RxUtil.strConvertBean(jsonStr,UserBean.class);
        }
        return  null;
    }

    private void setCacheUserInfo(UserBean bean){
        SharedPreferences sharedPreferences = MyApplication.getApplication().getSharedPreferences(SP_ACCOUNT_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_ACCOUNT_USER, RxUtil.beanConvertToStr(bean));
        editor.commit();
    }
}
