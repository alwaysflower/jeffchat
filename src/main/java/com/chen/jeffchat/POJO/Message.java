package com.chen.jeffchat.POJO;

public interface Message {
    int LoginRequestMessage = 0;
    int ChatRequestMessage = 1;

    int getType();
}
