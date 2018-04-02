package com.minh.wechatonline.model;

/**
 * Created by HP on 3/28/2018.
 */

public class Friend extends User{
    String id;
    String date;
    public Friend(){}

    public Friend(String date) {
        this.date = date;
    }

    public Friend(String email, String status, String date) {
        super(email, status);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
