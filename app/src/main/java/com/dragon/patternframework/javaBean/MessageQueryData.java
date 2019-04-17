package com.dragon.patternframework.javaBean;

import java.util.List;

public class MessageQueryData {

    private List<Message> payload;
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


    public List<Message> getPayload() {
        return payload;
    }

    public void setPayload(List<Message> payload) {
        this.payload = payload;
    }
}
