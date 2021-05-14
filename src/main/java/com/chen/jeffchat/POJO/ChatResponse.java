package com.chen.jeffchat.POJO;

import java.lang.reflect.Type;

public class ChatResponse implements Response {
    private int type = Response.ChatResponse;
    private boolean success;
    private String detail;

    public ChatResponse(int type, boolean success, String detail) {
        this.type = type;
        this.success = success;
        this.detail = detail;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public int getType() {
        return type;
    }
}
