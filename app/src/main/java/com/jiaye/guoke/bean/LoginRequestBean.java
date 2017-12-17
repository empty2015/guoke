package com.jiaye.guoke.bean;

/**
 * Created by Administrator on 2017/12/16.
 */

public class LoginRequestBean {
    private String mobile;
    private String password;
    private boolean isMobie = false;

    public boolean isMobie() {
        return isMobie;
    }

    public void setMobie(boolean mobie) {
        isMobie = mobie;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
