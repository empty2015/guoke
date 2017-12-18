package com.jiaye.guoke.module.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.guoke.R;
import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.base.component.CommonHeaderView;
import com.jiaye.guoke.base.component.CustomToast;
import com.jiaye.guoke.bean.AccountBean;
import com.jiaye.guoke.module.account.AccountManager;
import com.jiaye.guoke.module.account.AccountUtil;
import com.jiaye.guoke.net.ApiManager;
import com.jiaye.guoke.net.HttpCode;
import com.jiaye.guoke.net.interfaces.IHttpResult;
import com.jiaye.guoke.util.StringUtil;

/**
 * Created by Administrator on 2017/12/10.
 */

public class InputPhoneActivity extends InputBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("电话号码");
        setSubTitle(AccountManager.getInstance().getInputPhoneSubTitle());
        setActionText(AccountManager.getInstance().getPhoneBtnLabel());
        setInputHint("请输入手机号");
    }

    @Override
    protected boolean getActionState() {
        String phone = getInput();
        if (StringUtil.isPhone(phone)) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AccountManager.getInstance().backToOldType();
    }

    @Override
    protected void doAction() {
        AccountManager.getInstance().setMobile(getInput());
        if(AccountManager.getInstance().getAccountType() == AccountUtil.ACCOUNT_TYPE_REGISTER){
            checkMobile();
        }else{
            startActivity(new Intent(this,InputPwdActivity.class));
        }

    }

    private void checkMobile(){
        ApiManager.getInstance().checkMobile(getInput(), new IHttpResult<AccountBean>() {
            @Override
            public void onSuccess(AccountBean data) {
                if(data.getResult().equals("1")){
                    startActivity(new Intent(InputPhoneActivity.this,InputVerifyActivity.class));
                }else{
                    CustomToast.showToast(InputPhoneActivity.this,data.getMessage());
                }
            }

            @Override
            public void onFailure(HttpCode httpCode) {
                CustomToast.showToast(InputPhoneActivity.this,httpCode.getErrorMsg());
            }
        });
    }
}
