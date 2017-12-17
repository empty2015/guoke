package com.jiaye.guoke.base;


import android.content.Intent;

import com.jiaye.guoke.MainActivity;
import com.jiaye.guoke.R;
import com.jiaye.guoke.module.account.activity.RoleInfoActivity;
import com.jiaye.guoke.module.account.activity.WelcomeActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by Administrator on 2017/12/10.
 */

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        jump();
                    }
                });
    }

    private void jump(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
