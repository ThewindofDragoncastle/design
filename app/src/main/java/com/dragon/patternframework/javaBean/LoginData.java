package com.dragon.patternframework.javaBean;

import java.util.List;

public class LoginData {

    /**
     * payload :
     * success : true
     * msg : null
     * code : -1
     * timestamp : 1523852961
     */
    private User payload;
    private boolean success;
    private String msg;
    private int code;
    private int timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getPayload() {
        return payload;
    }

    public void setPayload(User payload) {
        this.payload = payload;
    }
}
