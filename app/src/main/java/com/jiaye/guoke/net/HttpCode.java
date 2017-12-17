package com.jiaye.guoke.net;

/**
 * Created by Administrator on 2017/12/16.
 */

public class HttpCode {
    private int errorCode;
    private String errorMsg;

    public HttpCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
