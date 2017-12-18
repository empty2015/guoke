package com.jiaye.guoke.sms;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.jiaye.guoke.sms.interfaces.OnSMSListener;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/12/18.
 */

public class SMSHandler extends EventHandler {
    OnSMSListener listener;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            int code = msg.what;
            if(code == 1){
                if (listener != null)
                    listener.onSuccess();
            }else{
                if (listener != null)
                    listener.onError(msg.obj.toString());
            }
        }
    };

    public SMSHandler(OnSMSListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void beforeEvent(int i, Object o) {
        super.beforeEvent(i, o);
    }

    @Override
    public void afterEvent(int event, int result, Object data) {
        if (result == SMSSDK.RESULT_COMPLETE) {
            //回调完成
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                //提交验证码成功
               handler.sendEmptyMessage(1);
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                //获取验证码成功
                handler.sendEmptyMessage(1);
            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                //返回支持发送验证码的国家列表
            }
        } else {
            ((Throwable) data).printStackTrace();
            Message message = new Message();
            message.what = -1;
            message.obj = ((Throwable) data).getMessage();
            handler.sendMessage(message);
        }
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
        listener = null;
    }
}
