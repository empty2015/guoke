package com.jiaye.guoke;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.module.home.fragment.AccountFragment;
import com.jiaye.guoke.module.home.fragment.DiscoveryFragment;
import com.jiaye.guoke.module.home.fragment.HomeFragment;
import com.jiaye.guoke.module.home.fragment.MessageFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup radioGroup;
    RadioButton rbHome;

    private Fragment curFragment = null;

    HomeFragment homeFragment;
    DiscoveryFragment discoveryFragment;
    MessageFragment messageFragment;
    AccountFragment accountFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        rbHome = (RadioButton)findViewById(R.id.rb_home);
    }

    @Override
    public void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
        rbHome.setChecked(true);
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String mess = "";
        switch (checkedId){
            case R.id.rb_home:
                if(homeFragment==null){
                    homeFragment = new HomeFragment();
                }
                changeTab(homeFragment);
                break;
            case R.id.rb_discovery:
                if(discoveryFragment==null){
                    discoveryFragment = new DiscoveryFragment();
                }
                changeTab(discoveryFragment);
                break;
            case R.id.rb_submit:
                mess = "发布";
                break;
            case R.id.rb_message:
                if(messageFragment==null){
                    messageFragment = new MessageFragment();
                }
                changeTab(messageFragment);
                break;
            case R.id.rb_account:
                if(accountFragment==null){
                    accountFragment = new AccountFragment();
                }
                changeTab(accountFragment);
                break;
        }
    }

    private void changeTab(Fragment fragment){
        if(curFragment == fragment)
            return;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction =  manager.beginTransaction();
        if(curFragment==null){//表示初始化
            transaction.add(R.id.fl_container,fragment);
        }else{
            if(!fragment.isAdded()){
                transaction.hide(curFragment)
                        .add(R.id.fl_container,fragment);
            }else{
                transaction.show(fragment)
                        .hide(curFragment);
            }

        }
        transaction.commit();
        curFragment = fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }
}
