package com.minh.wechatonline.model;

/**
 * Created by HP on 4/8/2018.
 */

public class Message {
    private String message, type;
    private String messageTime;

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
        this.messageTime = getMessageTime();

//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        int second = c.get(Calendar.SECOND);
//
//        messageTime = year + "-" + (month + 1) + "-" + day //
//                + " (" + hour + ":" + minute + ":" + second + ")";
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

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
