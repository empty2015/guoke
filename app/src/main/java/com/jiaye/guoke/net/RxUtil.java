package com.jiaye.guoke.net;

import android.support.annotation.NonNull;

import com.jiaye.guoke.net.interfaces.IHttpResult;
import com.jiaye.guoke.util.GsonManager;
import com.jiaye.guoke.util.NetWorkUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/16.
 */

public class RxUtil {

    static Map<String, Disposable> requestCache = new HashMap<>();

    private static void addRequest(String tag, Disposable disposable) {
        requestCache.put(tag, disposable);
    }

    private static void disposeRequest(String tag) {
        if (requestCache.containsKey(tag)) {
            requestCache.remove(tag).dispose();
        }
    }

    private static void clearCache(String tag) {
        requestCache.remove(tag);
    }


    public static void cancelRequest(String tag) {
        disposeRequest(tag);
    }

    public static <T> void request(final String tag, Observable<T> observable, @NonNull final IHttpResult<T> result) {
        if (!NetWorkUtil.isAvailableByPing()) {//网络不可用
            result.onFailure(new HttpCode(HttpEnum.HTTP_ERROR_CONNECT.getCode(), HttpEnum.HTTP_ERROR_CONNECT.getMsg()));
            return;
        }
        cancelRequest(tag);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        clearCache(tag);
                        result.onSuccess(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        clearCache(tag);
                        result.onFailure(new HttpCode(HttpEnum.HTTP_ERROR_UNKNOWN.getCode(), throwable.getMessage()));
                    }
                });
        addRequest(tag, disposable);
    }

    public static String beanConvertToStr(Object obj){
        try {
            return GsonManager.getInstance().getGson().toJson(obj,obj.getClass());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T strConvertBean(String json,Class<T> clazz){
        try {
            return GsonManager.getInstance().getGson().fromJson(json,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
