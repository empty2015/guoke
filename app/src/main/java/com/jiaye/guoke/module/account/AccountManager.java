package com.jiaye.guoke.module.account;

import java.util.Stack;

/**
 * Created by Administrator on 2017/12/16.
 */

public class AccountManager {
    private static volatile AccountManager instance;
    private AccountManager() {

    }
    public static AccountManager getInstance() {
        if (instance == null) {
            synchronized (AccountManager.class) {
                if (instance == null) {
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    private Stack<Integer> oldTypeStack = new Stack<>();
    private int accountType = AccountUtil.ACCOUNT_TYPE_NONE;
    private String mobile;
    private String verifyCode;
    private String pwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAccountType(int type){
        oldTypeStack.push(accountType);
        accountType = type;
    }
    public void backToOldType(){
        if(!oldTypeStack.isEmpty()){
            accountType = oldTypeStack.pop();
        }
    }

    public int getAccountType(){
        return accountType;
    }

    public String getInputPhoneSubTitle(){
        switch (accountType){
            case AccountUtil.ACCOUNT_TYPE_REGISTER:
                return "我们将使用你的手机号进行注册";
            case AccountUtil.ACCOUNT_TYPE_LOGIN:
                return "我们将使用你的手机号进行登录";
            case AccountUtil.ACCOUNT_TYPE_FORGET:
                return "我们将使用你的手机号码进行登录";
            default:
                return "";
        }
    }

    public String getInputPwdSubTitle(){
        switch (accountType){
            case AccountUtil.ACCOUNT_TYPE_REGISTER:
                return "请设置你的登录密码，6-12位数字字母";
            case AccountUtil.ACCOUNT_TYPE_LOGIN:
                return "请输入你的登录密码";
            case AccountUtil.ACCOUNT_TYPE_FORGET:
                return "请重新设置你的登录密码，6-12位数字字母";
            default:
                return "";
        }
    }

    public String getPhoneBtnLabel(){
        switch (accountType){
            case AccountUtil.ACCOUNT_TYPE_REGISTER:
                return "发送验证码";
            case AccountUtil.ACCOUNT_TYPE_LOGIN:
                return "下一步";
            case AccountUtil.ACCOUNT_TYPE_FORGET:
                return "下一步";
            default:
                return "";
        }
    }

    public String getPwdBtnLabel(){
        switch (accountType){
            case AccountUtil.ACCOUNT_TYPE_REGISTER:
                return "下一步";
            case AccountUtil.ACCOUNT_TYPE_LOGIN:
                return "登录";
            case AccountUtil.ACCOUNT_TYPE_FORGET:
                return "完成";
            default:
                return "";
        }
    }
}
