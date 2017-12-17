package com.jiaye.guoke.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/10.
 */

public class StringUtil {
    private static final String PHONE_REG = "1\\d{10}";
    private static final String PWD_REG =  "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static boolean isPhone(String number){
        return Pattern.matches(PHONE_REG,number);
    }

    public static boolean isPwd(String pwd){
        if(TextUtils.isEmpty(pwd))
            return false;
        return Pattern.matches(PWD_REG,pwd)&pwd.length()>5;
    }
}
