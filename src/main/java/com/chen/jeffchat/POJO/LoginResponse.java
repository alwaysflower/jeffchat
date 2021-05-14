package com.chen.jeffchat.POJO;

public class LoginResponse implements Response {
    private int type = Response.LoginResponse;
    private boolean success;
    private String detail;

    public LoginResponse(int type, boolean success, String detail) {
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
