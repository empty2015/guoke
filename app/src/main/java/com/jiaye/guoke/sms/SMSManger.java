package com.jiaye.guoke.sms;

import android.content.Context;

import com.jiaye.guoke.sms.interfaces.ISMSCallBack;
import com.jiaye.guoke.sms.interfaces.OnSMSListener;
import com.mob.MobSDK;

import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/12/18.
 */

public class SMSManger {
    private static SMSManger smsManger;
    ISMSCallBack callBack;
    private SMSManger(){
    }
    public static SMSManger getInstance(){
        if(smsManger==null){
            smsManger = new SMSManger();
        }
        return smsManger;
    }

    private OnSMSListener listener = new OnSMSListener() {
        @Override
        public void onSuccess() {
            if(callBack!=null){
                callBack.onSuccess();
            }
            callBack = null;
        }
        @Override
        public void onError(String message) {
            if(callBack!=null){
                callBack.onFailed(message);
            }
            callBack = null;
        }
    };

    public void init(Context context){
        MobSDK.init(context, "1d753cde3af56","cbda86f9ab013abf711ae647919fb78e");
        SMSSDK.registerEventHandler(new SMSHandler(listener));
    }

    public void sendVerifyCode(String phone, ISMSCallBack callBack){
        this.callBack = callBack;
        SMSSDK.getVerificationCode("86",phone);
    }

    public void verifyCode(String phone,String code,ISMSCallBack callBack){
        this.callBack = callBack;
        SMSSDK.submitVerificationCode("86",phone,code);
    }
}
