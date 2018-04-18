package com.minh.wechatonline.model;

import java.util.Calendar;

/**
 * Created by HP on 4/8/2018.
 */

public class Message {
    private String message, type;
    private long messageTime;

    private boolean seen;
    private String from;

    public Message() {
    }

    public Message(String from) {
        this.from = from;
    }

    public Message(String from, String message, boolean seen,String type) {
        this.from = from;
        this.message = message;
        this.seen = seen;
        this.type = type;
        messageTime =  Calendar.getInstance().getTimeInMillis();




    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
