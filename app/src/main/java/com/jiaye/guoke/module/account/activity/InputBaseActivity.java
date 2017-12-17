package com.jiaye.guoke.module.account.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiaye.guoke.R;
import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.base.component.CommonHeaderView;
import com.jiaye.guoke.base.component.CustomToast;
import com.jiaye.guoke.util.StringUtil;

/**
 * Created by Administrator on 2017/12/10.
 */

public abstract class InputBaseActivity extends BaseActivity {
    CommonHeaderView headerView;
    EditText input;
    TextView tvAction;
    TextView tvTip1;
    TextView tvTip2;
    TextView tvNumTip;
    @Override
    public int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    public void initView() {
        headerView = (CommonHeaderView)findViewById(R.id.headerview);
        headerView.setSplitViewState(View.INVISIBLE);
        input = (EditText)findViewById(R.id.edit_input);
        tvAction = (TextView)findViewById(R.id.tv_action);
        tvTip1 = (TextView)findViewById(R.id.tv_tip1);
        tvTip2 = (TextView)findViewById(R.id.tv_tip2);
        tvNumTip = (TextView)findViewById(R.id.tv_num_tip);
    }

    public void setNumTipVisibility(int visibility){
        tvNumTip.setVisibility(visibility);
    }

    public void setActionText(String text){
        tvAction.setText(text);
    }



    @Override
    public void initListener() {
        headerView.setOnHeaderClickEvent(new CommonHeaderView.OnHeaderClickEvent() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {
                onHeaderRightClick();
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvAction.setEnabled(getActionState());
            }
        });
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction();
            }
        });
    }

    protected void onHeaderRightClick(){

    }

    protected void setRightText(String label){
        headerView.setRightText(label);
    }

    protected void setTitle(String title){
        tvTip1.setText(title);
    }

    protected void setSubTitle(String subTitle){
        tvTip2.setText(subTitle);
    }

    public void setInputHint(String hint){
        input.setHint(hint);
    }

    protected String getInput(){
        return input.getText().toString();
    }

    protected abstract boolean getActionState();
    protected abstract void doAction();

}
