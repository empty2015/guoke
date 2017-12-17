package com.jiaye.guoke.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.guoke.base.interfaces.IViewInterface;

/**
 * Created by Administrator on 2017/12/10.
 */

public abstract class BaseFragment extends Fragment implements IViewInterface{

    protected View layoutView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),null);
        layoutView = view;
        initView();
        initListener();
        return view;
    }
}
