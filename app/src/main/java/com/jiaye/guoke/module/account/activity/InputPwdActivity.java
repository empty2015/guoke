package com.jiaye.guoke.module.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.jiaye.guoke.base.component.CustomToast;
import com.jiaye.guoke.bean.AccountBean;
import com.jiaye.guoke.module.account.AccountManager;
import com.jiaye.guoke.module.account.AccountUtil;
import com.jiaye.guoke.module.account.LoginManager;
import com.jiaye.guoke.net.ApiManager;
import com.jiaye.guoke.net.HttpCode;
import com.jiaye.guoke.net.interfaces.IHttpResult;
import com.jiaye.guoke.util.StringUtil;

/**
 * Created by Administrator on 2017/12/11.
 */

public class InputPwdActivity extends InputBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("输入密码");
        setSubTitle(AccountManager.getInstance().getInputPwdSubTitle());
        setInputHint("请输入密码");
        setActionText(AccountManager.getInstance().getPwdBtnLabel());
        setNumTipVisibility(View.GONE);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});

        if(AccountManager.getInstance().getAccountType() == AccountUtil.ACCOUNT_TYPE_LOGIN){
            setRightText("忘记密码");
        }
    }

    @Override
    protected void onHeaderRightClick() {
       //忘记密码
        AccountManager.getInstance().setAccountType(AccountUtil.ACCOUNT_TYPE_FORGET);
        startActivity(new Intent(this,InputPhoneActivity.class));
    }


    @Override
    public void initListener() {
        super.initListener();
        input.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData().toCharArray();
                return data;
            }
        });
    }

    @Override
    protected boolean getActionState() {
       return StringUtil.isPwd(getInput());
    }

    private String getStringData(){
        return "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
    }

        @Override
    protected void doAction() {
            AccountManager.getInstance().setPwd(getInput());
            if(AccountManager.getInstance().getAccountType() == AccountUtil.ACCOUNT_TYPE_REGISTER){
                startActivity(new Intent(this,RoleInfoActivity.class));
            }else if(AccountManager.getInstance().getAccountType() == AccountUtil.ACCOUNT_TYPE_LOGIN){
                doLogin();
            }else if(AccountManager.getInstance().getAccountType() == AccountUtil.ACCOUNT_TYPE_FORGET){
                doForgetPwd();
            }

    }

    private void doLogin(){
        String mobile = AccountManager.getInstance().getMobile();
        String pwd = AccountManager.getInstance().getPwd();
        ApiManager.getInstance().login(mobile, pwd, new IHttpResult<AccountBean>() {
            @Override
            public void onSuccess(AccountBean data) {
                if(data.getResult().equals("1")){
                    LoginManager.getInstance().setUserInfo(data.getUser());
                }else{
                    CustomToast.showToast(InputPwdActivity.this,data.getMessage());
                }
            }

            @Override
            public void onFailure(HttpCode httpCode) {
                CustomToast.showToast(InputPwdActivity.this,httpCode.getErrorMsg());
            }
        });
    }

    private void doForgetPwd(){
        String mobile = AccountManager.getInstance().getMobile();
        String pwd = AccountManager.getInstance().getPwd();
        String verifyCode = AccountManager.getInstance().getVerifyCode();
    }
}
