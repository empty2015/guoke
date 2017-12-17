package com.jiaye.guoke.net;

/**
 * Created by Administrator on 2017/12/16.
 */

public enum HttpEnum {
    HTTP_ERROR_UNKNOWN(-1,""),
    HTTP_ERROR_CONNECT(100,"网络无法连接");

    private int code;
    private String msg;
    private HttpEnum(int code,String message){
        this.code = code;
        this.msg  = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
