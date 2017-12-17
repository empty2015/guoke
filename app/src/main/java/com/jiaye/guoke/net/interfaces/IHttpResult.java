package com.jiaye.guoke.net.interfaces;

import com.jiaye.guoke.net.HttpCode;
import com.jiaye.guoke.net.HttpEnum;

/**
 * Created by Administrator on 2017/12/16.
 */

public interface IHttpResult<T> {
    public void onSuccess(T data);
    public void onFailure(HttpCode httpCode);
}
