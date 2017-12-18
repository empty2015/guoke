package com.jiaye.guoke.sms.interfaces;

/**
 * Created by Administrator on 2017/12/18.
 */

public interface ISMSCallBack {
    public void onSuccess();
    public void onFailed(String error);
}
