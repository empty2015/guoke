package com.jiaye.guoke.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/12/16.
 */

public abstract class BaseApplication extends Application {
    private static Context context;

    public static Context getApplication(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
