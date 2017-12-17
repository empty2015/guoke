package com.jiaye.guoke.base.interfaces;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface IViewInterface {
    /**
     * 获取布局ID
     * @return
     */
    abstract int getLayoutId();

    /**
     * 初始化view
     */
    abstract void initView();

    /**
     * 初始化监听
     */
    abstract void initListener();
}
