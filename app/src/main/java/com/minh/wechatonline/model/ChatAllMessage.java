package com.minh.wechatonline.model;

import java.util.Calendar;

/**
 * Created by HP on 3/31/2018.
 */

public class ChatAllMessage {
    //private String id;
    private String messageText;
    private String messageUser;
    private String messageTime;
    private String imageProfile;
    public ChatAllMessage() {

    }

    public ChatAllMessage(String messageText, String messageUser) {

        this.messageText = messageText;
        this.messageUser = messageUser;
        //this.imageProfile = imageProfile;
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);


        messageTime = year + "-" + (month + 1) + "-" + day //
                + " (" + hour + ":" + minute + ":" + second + ")";
    }




    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}
