package com.minh.wechatonline.model;

/**
 * Created by HP on 3/28/2018.
 */

public class Friend {

    public String date;
    public String image;
    public Friend() {
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Friend(String date,String image) {

        this.date = date;
        this.image =  image;
    }

}
