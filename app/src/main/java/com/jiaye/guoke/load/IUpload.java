package com.jiaye.guoke.load;

/**
 * Created by Administrator on 2017/12/18.
 */

public interface IUpload {
    public void onSuccess(String url);
    public void onFailed(String message);
}
