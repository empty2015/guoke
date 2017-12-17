package com.jiaye.guoke.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jiaye.guoke.base.interfaces.IViewInterface;

/**
 * Created by Administrator on 2017/12/9.
 */

public abstract class BaseActivity extends FragmentActivity implements IViewInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initListener();
    }

}
