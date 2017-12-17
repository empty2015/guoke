package com.jiaye.guoke.module.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.guoke.R;
import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.module.account.AccountManager;
import com.jiaye.guoke.module.account.AccountUtil;

/**
 * Created by Administrator on 2017/12/9.
 */

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{
    TextView tvPrivacy;
    TextView tvProtocol;
    TextView tvLogin;
    TextView tvRegister;

    ImageView ivClose;
    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        tvPrivacy = (TextView)findViewById(R.id.tv_privacy);
        tvProtocol = (TextView)findViewById(R.id.tv_protocol);
        tvLogin = (TextView)findViewById(R.id.tv_login);
        tvRegister = (TextView)findViewById(R.id.tv_register);
        ivClose = (ImageView)findViewById(R.id.iv_close);
    }

    @Override
    public void initListener() {
        tvPrivacy.setOnClickListener(this);
        tvProtocol.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_privacy:
                showPrivacy();
                break;
            case R.id.tv_protocol:
                showProtocol();
                break;
            case R.id.tv_login:
                goLogin();
                break;
            case R.id.tv_register:
                goRegister();
                break;
            case R.id.iv_close:
                onBackPressed();
                break;
        }
    }

    /**
     * 隐私策略
     */
    private void showPrivacy(){

    }

    /**
     * 注册协议
     */
    private void showProtocol(){

    }

    /**
     * 登录
     */
    private void goLogin(){
        AccountManager.getInstance().setAccountType(AccountUtil.ACCOUNT_TYPE_LOGIN);
        startActivity(new Intent(this,InputPhoneActivity.class));
    }

    /**
     * 注册
     */
    private void goRegister(){
        AccountManager.getInstance().setAccountType(AccountUtil.ACCOUNT_TYPE_REGISTER);
        startActivity(new Intent(this,InputPhoneActivity.class));
    }
}
