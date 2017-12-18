package com.jiaye.guoke;

import com.jiaye.guoke.base.BaseApplication;
import com.jiaye.guoke.sms.SMSManger;

/**
 * Created by Administrator on 2017/12/16.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initSMS();
    }
    private void initSMS(){
        SMSManger.getInstance().init(this);
    }
}
