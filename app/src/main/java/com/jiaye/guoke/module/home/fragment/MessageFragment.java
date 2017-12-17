package com.jiaye.guoke.module.home.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jiaye.guoke.R;
import com.jiaye.guoke.base.BaseFragment;
import com.jiaye.guoke.module.account.activity.WelcomeActivity;

/**
 * Created by Administrator on 2017/12/10.
 */

public class MessageFragment extends BaseFragment {
    TextView tvAction;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        tvAction = (TextView)layoutView.findViewById(R.id.tv_action);
    }

    @Override
    public void initListener() {
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction();
            }
        });
    }

    private void doAction(){
        getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
    }
}
