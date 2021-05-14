package com.chen.jeffchat.POJO;

public class LoginRequestMessage implements Message {
    private String userId;
    private String password;

    public LoginRequestMessage(String userId, String password){
        this.password = password;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int getType() {
        return Message.LoginRequestMessage;
    }
}
