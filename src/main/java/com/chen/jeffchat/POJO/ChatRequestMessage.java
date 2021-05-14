package com.chen.jeffchat.POJO;

public class ChatRequestMessage implements Message {
    private String fromId;
    private String toId;
    private String content;

    public ChatRequestMessage(String fromId, String toId, String content) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return Message.ChatRequestMessage;
    }
}
