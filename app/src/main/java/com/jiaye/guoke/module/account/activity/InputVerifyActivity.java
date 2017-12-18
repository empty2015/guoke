package com.jiaye.guoke.module.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.base.component.CustomToast;
import com.jiaye.guoke.module.account.AccountManager;
import com.jiaye.guoke.net.ApiManager;
import com.jiaye.guoke.sms.SMSManger;
import com.jiaye.guoke.sms.interfaces.ISMSCallBack;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2017/12/11.
 */

public class InputVerifyActivity extends InputBaseActivity {

    private final String TIP = "验证码发送成功,%d秒后可重新发送";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("验证码");
        setInputHint("请输入验证码");
        setNumTipVisibility(View.GONE);
        setActionText("下一步");
        sendVerifyCode();
    }

    private void sendVerifyCode(){
        String mobile = AccountManager.getInstance().getMobile();
        SMSManger.getInstance().sendVerifyCode(mobile, new ISMSCallBack() {
            @Override
            public void onSuccess() {
                doSendVerifyCode();
            }

            @Override
            public void onFailed(String error) {
                CustomToast.showToast(InputVerifyActivity.this,error);
                onBackPressed();
            }
        });
    }

    private void doSendVerifyCode(){
        updateStr(60);
        startTimer(60);
    }

    private void updateStr(int time){
        setSubTitle(String.format(TIP,time));
    }

    private void startTimer(final int time){
        final int countTime = time;
        Observable obser = Observable.interval(0,1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return  countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
         obser.safeSubscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                updateStr(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected boolean getActionState() {
        String input = getInput();
        if(input.length()>3){
            return true;
        }
        return false;
    }

    @Override
    protected void doAction() {
        String mobile = AccountManager.getInstance().getMobile();
        String code = getInput();
        SMSManger.getInstance().verifyCode(mobile,code,new ISMSCallBack(){
            @Override
            public void onSuccess() {
                AccountManager.getInstance().setVerifyCode(getInput());
                startActivity(new Intent(InputVerifyActivity.this,InputPwdActivity.class));
            }
            @Override
            public void onFailed(String error) {
                CustomToast.showToast(InputVerifyActivity.this,error);
            }
        });

    }
}
